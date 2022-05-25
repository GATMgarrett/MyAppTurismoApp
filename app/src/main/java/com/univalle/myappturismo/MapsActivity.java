package com.univalle.myappturismo;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.univalle.myappturismo.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private Location location;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setUpMapIfNeeded();
        
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     *
     * Manipula el mapa una vez disponible.
     * Esta devolución de llamada se activa cuando el mapa está listo para usarse.
     * Aquí es donde podemos añadir marcadores o líneas, añadir oyentes o mover la cámara. En este caso,
     * solo agregamos un marcador cerca de Sydney, Australia.
     * Si los servicios de Google Play no están instalados en el dispositivo, se le pedirá al usuario que instale
     * dentro del SupportMapFragment. Este método solo se activará una vez que el usuario haya
     * instaló los servicios de Google Play y volvió a la aplicación.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
/*
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }
    private void setUpMapIfNeeded() {
        // Si el nMap esta null entonces es porque no se instancio el mapa.
        if (mMap == null) {
            // Intenta obtener el mapa del SupportMapFragment.
            ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull GoogleMap googleMap) {
                    mMap = googleMap;
                }
            });
            // Comprueba si hemos tenido exito en la obtencion del mapa.
            if (mMap != null) {
                setUpMap();
            }
        }
    }
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new
                LatLng(-6.7602212, -79.8658961)).title("Android Facil"));
    }*/
}