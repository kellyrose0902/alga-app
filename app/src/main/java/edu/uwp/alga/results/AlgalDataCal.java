package edu.uwp.alga.results;

import java.util.ArrayList;

import static java.lang.Math.exp;
import static java.lang.Math.min;
import static java.lang.Math.pow;

/**
 * Created by Kelly on 11/16/2015.
 */
public class AlgalDataCal {


    // All of the require constant
    public static final float R0_MIN = 0.0001f;
    public static final float R0_MAX = 0.065f;
    public static final int N0_MIN = 5;
    public static final float GROWTH_RATE_SCALE = 1.635f;
    public static final int TIMESHIFT = 21;


    public float N0 = N0_MIN;
    // Parameters main
    public float total_chla;
    public float cyano_chla;
    public float Pbot;
    public float surtemp;
    public float bottemp;
    public float depth;
    public float lux;



    // Direct constructor
    public AlgalDataCal(float total_chla, float cyano_chla,float Pbot,float surtemp, float bottemp, float depth,float lux){
        this.total_chla = total_chla;
        this.cyano_chla = cyano_chla;
        this.Pbot = Pbot;
        this.surtemp = surtemp;
        this.bottemp = bottemp;
        this.depth = depth;
        this.lux = lux;
    }
    //Estimate constructor
    public AlgalDataCal(float SD_value, float DO_value,float Pbot,float surtemp, float bottemp, float depth,float lux,boolean estimate){
        this.cyano_chla = AlgalDataCal.estimateCyanoChla(SD_value,surtemp);
        if (DO_value>0) this.total_chla = AlgalDataCal.estimateTotalChla(SD_value, DO_value);
        this.Pbot = Pbot;
        this.surtemp = surtemp;
        this.bottemp = bottemp;
        this.depth = depth;
        this.lux = lux;
    }

    // static methods to return estimate Chla
    private static float estimateTotalChla(float SD_value,float DO_value){
        return -6.4775f + 21.6396f * (1/SD_value) + 0.0006f * (float)pow(DO_value,2);
    }
    private static float estimateCyanoChla(float SD_value, float surtemp){
        return 0.409f - 0.7486f * surtemp + 17.6979f * (1/SD_value);
    }

    public float getK1(){
        float pav = Pbot/depth;
        float K1 = 1875 * pav - 7.5f;
        if (K1<0) K1 = 0;
        return K1;
    }

    public float getK2(){
        float pav = Pbot/depth;
        float K2 = 1625 * pav - 12.5f;
        if (K2<0) K2 = 0;
        return K2;
    }

    public float getR01(){
        float pav = Pbot/depth;
        float tempdiff;

        float R01 = R0_MIN;
        tempdiff = surtemp - bottemp;
        if (surtemp > 15 && pav> 0.02f && tempdiff <4){
            R01 = R0_MAX * min((-tempdiff/3+4/3),10*pav);
            if (tempdiff <= 1 & pav <= 0.1f) R01 = R0_MAX*10*pav;
            if (tempdiff > 1 & pav > 0.1f) R01 = R0_MAX*(-tempdiff/3+4/3);
            if (tempdiff <= 1 & pav > 0.1f) R01 = R0_MAX;
        }
        R01 = R01* (float)exp(pow(-(0.17f)*(surtemp-27),2));
        if(lux<12000) R01 = R01 * lux/12000;
        return R01;
    }

    public float getR02(){
        float pav = Pbot/depth;
        float tempdiff;
        float R02 = R0_MIN;
        tempdiff = surtemp - bottemp;
        if (surtemp > 18 && pav> 0.02f && tempdiff <4){
            R02 = R0_MAX * min((-tempdiff/3+4/3),10*pav);
            if (tempdiff <= 1 & pav <= 0.1f) R02 = R0_MAX*10*pav;
            if (tempdiff > 1 & pav > 0.1f) R02 = R0_MAX*(-tempdiff/3+4/3);
            if (tempdiff <= 1 & pav > 0.1f) R02 = R0_MAX;
        }
        R02 = R02* (float)exp(pow(-(0.17)*(surtemp-27),2));
        if(lux<12000) R02 = R02 * lux/12000;
        return R02;
    }

    public float getT_CUR_CHL(boolean isTotal){
        float t_cur_Chl;
        float R0,K;
        float N_CUR_CHL;
        if(isTotal){
            R0 = getR01();
            K = getK1();
            N_CUR_CHL = total_chla;
        }
        else{
            R0 = getR02();
            K = getK2();
            N_CUR_CHL = cyano_chla;
        }
        t_cur_Chl =-(1/(R0*GROWTH_RATE_SCALE))*(float)Math.log10((N0*K/N_CUR_CHL-N0)/(K-N0))+TIMESHIFT;
        return t_cur_Chl;
    }
    public ArrayList<Float> getTotalChlaDataSet(){
        ArrayList<Float> totalDataSet = new ArrayList<>();
        totalDataSet.add(0f);
        int q = 0;
        float K = getK1();
        float R0 = getR01();
        float Nt_continuous = (N0*K)/(N0+(K-N0)*(float)exp(-R0*GROWTH_RATE_SCALE*(q-TIMESHIFT)));
        totalDataSet.add(Nt_continuous);
        while (K-Nt_continuous>0.1&&q<360){
            q += 1;
            Nt_continuous = (N0*K)/(N0+(K-N0)*(float)exp(-R0*GROWTH_RATE_SCALE*(q-TIMESHIFT)));
            totalDataSet.add(Nt_continuous);

        }
        int peak = q;
        for (q = peak + 1; q<=400; q++){
            Nt_continuous = (N0*K)/(N0+(K-N0)*(float)exp(-R0*GROWTH_RATE_SCALE*(-(q-2*peak)-TIMESHIFT)));
            totalDataSet.add(Nt_continuous);
        }
        return totalDataSet;
    }

    public ArrayList<Float> getCyanoChlaDataSet(){
        ArrayList<Float> cyanoDataSet = new ArrayList<>();
        cyanoDataSet.add(0f);
        int q = 0;
        float K = getK2();
        float R0 = getR02();
        float Nt_continuous = (N0*K)/(N0+(K-N0)*(float)exp(-R0*GROWTH_RATE_SCALE*(q-TIMESHIFT)));
        cyanoDataSet.add(Nt_continuous);
        while (K-Nt_continuous>0.1&&q<360){
            q += 1;
            Nt_continuous = (N0*K)/(N0+(K-N0)*(float)exp(-R0*GROWTH_RATE_SCALE*(q-TIMESHIFT)));
            cyanoDataSet.add(Nt_continuous);

        }
        int peak = q;
        for (q = peak + 1; q<=400; q++){
            Nt_continuous = (N0*K)/(N0+(K-N0)*(float)exp(-R0*GROWTH_RATE_SCALE*(-(q-2*peak)-TIMESHIFT)));
            cyanoDataSet.add(Nt_continuous);
        }
        return cyanoDataSet;
    }
}
