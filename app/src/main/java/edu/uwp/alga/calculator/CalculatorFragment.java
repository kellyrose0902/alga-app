package edu.uwp.alga.calculator;

/**
 * Copyright 2015 UW-Parkside, Harleen Kaur, Hanh Le, Francisco Mateo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import edu.uwp.alga.ChlaActivity;
import edu.uwp.alga.R;
import edu.uwp.alga.SubmitActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalculatorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalculatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalculatorFragment extends Fragment implements View.OnClickListener {
    public View rootView;
    Button setChlbutton;
    Button submitData;
    EditText POtext;
    EditText TempSurtext;
    EditText TempBottext;
    EditText Depthtext;
    /**
     * The fragment argument representing the section number for this
     * fragment. Static because it's shared across all instances of
     * this fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public CalculatorFragment() {}

    /**
     * Returns a new instance of this fragment for the given section number.
     *
     * @param sectionNumber Section number for this fragment
     * @return new instance of {@link CalculatorFragment}
     */
    public static CalculatorFragment newInstance(int sectionNumber) {
        CalculatorFragment fragment = new CalculatorFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_calculator, container, false);

        setChlbutton = (Button)rootView.findViewById(R.id.buttonChla);
        setChlbutton.setOnClickListener(this);
        submitData = (Button)rootView.findViewById(R.id.SubmitAll);
        submitData.setOnClickListener(this);
        POtext = (EditText)rootView.findViewById(R.id.po_edit);
        TempSurtext = (EditText)rootView.findViewById(R.id.temp_surface_edit);
        TempBottext = (EditText) rootView.findViewById(R.id.temp_bottom_edit);
        Depthtext = (EditText) rootView.findViewById(R.id.lake_depth_edit);
        return rootView;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonChla:
                Intent intentChl = new Intent(getActivity(), ChlaActivity.class);
                startActivity(intentChl);
                break;
            case R.id.SubmitAll:
                Intent intentData = new Intent(getActivity(), SubmitActivity.class);
                startActivity(intentData);

        }
    }
}
