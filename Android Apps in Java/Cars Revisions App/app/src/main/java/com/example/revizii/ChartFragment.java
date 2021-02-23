package com.example.revizii;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


public class ChartFragment extends Fragment {

    private ArrayList<Revision> revisions;

    public ChartFragment() {
    }

    public static ChartFragment newInstance(ArrayList<Revision> revisions) {
        ChartFragment fragment = new ChartFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("revisions_for_chart", revisions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            revisions = getArguments().getParcelableArrayList("revisions_for_chart");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        ConstraintLayout layout = view.findViewById(R.id.frameLayout);
        layout.addView(new Grafic(getActivity()));
        return view;
    }

    protected class Grafic extends View {

        public Grafic(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            //canvas.drawPaint(paint);
            //paint.setStyle(Paint.Style.FILL);
            int x = getWidth();
            int y = getHeight();

            if(revisions!=null) {
                for (int i = 0; i < revisions.size(); i++) {
                    Revision rev = revisions.get(i);
                    Random random  = new Random();
                    paint.setColor(Color.argb(200, random.nextInt(256), random.nextInt(256), random.nextInt(256)));
                    canvas.drawCircle(x/(i+1), y/(i+1), rev.getPret() / 2, paint);
                    paint.setColor(Color.BLACK);
                    paint.setTextSize(30);
                    canvas.drawText(rev.getModelMasina()+" - "+rev.getPret(),x/(i+1), y/(i+2) , paint);
                }
            }


        }
    }
}