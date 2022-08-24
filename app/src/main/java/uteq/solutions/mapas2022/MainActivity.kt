package uteq.solutions.mapas2022

import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback,GoogleMap.OnMapClickListener , GoogleMap.OnMarkerClickListener {
    lateinit var mMap:GoogleMap
    var puntos:ArrayList<LatLng> = ArrayList<LatLng>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment: SupportMapFragment = getSupportFragmentManager()
            .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this);

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        val camUpd1: CameraUpdate =
            CameraUpdateFactory.newLatLngZoom(
                LatLng(-1.0799, -79.50133), 25F);

        mMap.moveCamera(camUpd1);

        mMap.setOnMapClickListener(this)
        mMap.setOnMarkerClickListener (this)
    }

    override fun onMapClick(point: LatLng) {


        puntos.add(point)
        mMap.addMarker(
            MarkerOptions().position(point)
                .title("lugar"+puntos.size)
        )


        if(puntos.size==4){
        var lineas: PolylineOptions= PolylineOptions()

        for (punto:LatLng in puntos)
            lineas.add(punto)
        lineas.add(puntos.get(0))
        lineas.width(8F)

        mMap.addPolyline(lineas)

        puntos.clear()

        }

    }
// borrar marcador
    override fun onMarkerClick(p0: Marker): Boolean {
      p0.remove()
    var contador=0

    //
    var position= LatLng(p0.position.latitude,p0.position.longitude)
    if(puntos.size>0){
        while(contador<puntos.size)
        {
            var position2=LatLng(puntos[contador].latitude,puntos[contador].longitude)
            if(position2==position)
            {
                puntos.removeAt(contador)

            }
            contador++
        }
    }
        return false
    }
}