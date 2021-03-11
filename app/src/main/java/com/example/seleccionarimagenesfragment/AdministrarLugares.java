package com.example.seleccionarimagenesfragment;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdministrarLugares#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdministrarLugares extends Fragment
{
    private TextView TvImgSeleccionada;
    private Button BtnImgPortada, BtnGuardarLugar, BtnListaLugar;
    private ImageButton BtnCargarFotos;
    private EditText EdtNombreLuagar, EdtLocationLugar, EdtDescripcionLugar;
    private GridView GridImgs;
    Uri IMG_uri;
    boolean Galery = false;
    int PICK_IMAGE = 100;

    GridViewAdapter baseAdapter;

    List<Uri> Lista = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdministrarLugares() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_administrar_lugares, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        BtnImgPortada = getView().findViewById(R.id.BtnImgPortada);
        BtnCargarFotos = getView().findViewById(R.id.BtnCargarFotos);
        BtnGuardarLugar = getView().findViewById(R.id.BtnGuardarLugar);
        BtnListaLugar = getView().findViewById(R.id.BtnListaLugar);
        GridImgs = getView().findViewById(R.id.GridImgs);

        TvImgSeleccionada = getView().findViewById(R.id.TvImgSeleccionada);
        EdtNombreLuagar = getView().findViewById(R.id.EdtNombreLugar);
        EdtLocationLugar = getView().findViewById(R.id.EdtLocationLugar);
        EdtDescripcionLugar = getView().findViewById(R.id.EdtDescripcionLugar);


        BtnCargarFotos.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                GaleriaMultiple();
            }
        });

        BtnImgPortada.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                GaleriaSimple();
            }
        });
    }

    public void GaleriaSimple()
    {
        Intent OpenSimpleGalery = new Intent();
        OpenSimpleGalery.setType("image/*");
        OpenSimpleGalery.putExtra(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(OpenSimpleGalery, "Seleccione la aplicación..." ), 10);
    }

    public void GaleriaMultiple()
    {
        Intent OpenGalery = new Intent();
        OpenGalery.setType("image_path/*");
        OpenGalery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        OpenGalery.setAction(OpenGalery.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(OpenGalery, "Seleccione las imagenees...") , PICK_IMAGE);
        Galery = true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == 10)
        {
            Uri path = data.getData();
            TvImgSeleccionada.setText(path.toString());
        }

        if(Galery)
        {
            ClipData clipData = data.getClipData();

            if(resultCode == RESULT_OK && requestCode == PICK_IMAGE)
            {
                if(clipData == null)
                {
                    IMG_uri = data.getData();
                    Lista.add(IMG_uri);
                }
                else
                {
                    for(int i = 0; i < clipData.getItemCount(); i++)
                    {
                        Lista.add(clipData.getItemAt(i).getUri());
                    }
                }
            }
            baseAdapter = new GridViewAdapter(getView().getContext(), Lista);
            GridImgs.setAdapter(baseAdapter);
        }
    }

    //OTROS

    public static AdministrarLugares newInstance(String param1, String param2)
    {
        AdministrarLugares fragment = new AdministrarLugares();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
}