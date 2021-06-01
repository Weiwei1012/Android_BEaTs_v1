package com.weiwei.beats

import android.location.Address
import android.location.Geocoder
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.weiwei.beats.databinding.FragmentMapsBinding

class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapsBinding
    private lateinit var myMap : GoogleMap
    private lateinit var args: MapsFragmentArgs


//    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
////        val sydney = LatLng(-34.0, 151.0)
////        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
////        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            binding = DataBindingUtil.inflate(inflater,R.layout.fragment_maps,container,false)

            args = MapsFragmentArgs.fromBundle(requireArguments())

            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync(this)

            return binding.root
        }


    override fun onMapReady(googleMap: GoogleMap) {

        var addressList: List<Address>

        myMap = googleMap

        val geocoder = Geocoder(context)
        addressList = geocoder.getFromLocationName(args.address, 1)

//        val sydney = LatLng(-34.0, 151.0)
//        myMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        myMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        if (!addressList.isNullOrEmpty()) {
            val location = LatLng(addressList[0].latitude, addressList[0].longitude)
            myMap.addMarker(MarkerOptions().position(location).title(args.name))
            //zoom = 15f
            myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16.5f))
        }
    }
}