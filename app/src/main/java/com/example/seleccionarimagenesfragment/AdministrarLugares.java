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
import android.widget.Toast;

import java.io.File;
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
    private EditText EdtNombreLugar, EdtLocationLugar, EdtDescripcionLugar;
    private GridView GridImgs;
    Uri IMG_uri;
    boolean Portada = false;
    boolean Galery = false;

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
        EdtNombreLugar = getView().findViewById(R.id.EdtNombreLugar);
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

        BtnGuardarLugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               Comprobar();
            }
        });

        BtnListaLugar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                /*
                Intent OpenList = new Intent(AdministrarLugares.this, );
                startActivity(OpenList);*/
            }
        });
    }

    public void GaleriaSimple()
    {
        Intent OpenSimpleGalery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        OpenSimpleGalery.setType("image/*");
        startActivityForResult(Intent.createChooser(OpenSimpleGalery, "Seleccione la aplicación..." ), 10);
    }

    public void GaleriaMultiple()
    {
        Intent OpenGalery = new Intent();
        OpenGalery.setType("image/*");
        OpenGalery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        OpenGalery.setAction(OpenGalery.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(OpenGalery, "Seleccione las imagenes...") , 1000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK)
        {
            if(requestCode == 10)
            {
                Uri path = data.getData();
                Portada = true;
            }
            else if(requestCode == 1000)
            {
                ClipData clipData = data.getClipData();

                if(clipData == null)
                {
                    IMG_uri = data.getData();
                    Lista.add(IMG_uri);
                    Galery = true;
                }
                else
                {
                    for(int i = 0; i < clipData.getItemCount(); i++)
                    {
                        Lista.add(clipData.getItemAt(i).getUri());
                    }
                    Galery = true;
                }
            }
        }

        baseAdapter = new GridViewAdapter(getView().getContext(), Lista);
        GridImgs.setAdapter(baseAdapter);
    }

    public boolean Comprobar()
    {
        if(Portada && Galery && !EdtNombreLugar.getText().toString().isEmpty() && !EdtLocationLugar.getText().toString().isEmpty() && !EdtDescripcionLugar.getText().toString().isEmpty())
        {
            return true;
        }
        else
        {
            Toast.makeText(getActivity().getApplicationContext(), "Ingrese los datos e imagenes requeridas" , Toast.LENGTH_LONG).show();
            return false;
        }
    }

    //OTROS IGNORAR
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