package com.konum.konumbilgisialma

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.konum.konumbilgisialma.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private var izin=0
    private lateinit var flpc: FusedLocationProviderClient
    private lateinit var locationTask : Task<Location>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        flpc=LocationServices.getFusedLocationProviderClient(this)
        binding.konumAl.setOnClickListener {
            izin=ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)

            if(izin !=PackageManager.PERMISSION_GRANTED){ //izin onaylanmamışsa
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),100)
            }else{ //izin onaylanmışsa
                locationTask=flpc.lastLocation
                konumAl()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun konumAl(){
        locationTask.addOnSuccessListener {
            if(it!=null){
                binding.enlem.text="Enlem : ${it.latitude}"
                binding.boylam.text="Boylam : ${it.longitude}"
            }else{
                binding.enlem.text="Enlem : Alınamadı"
                binding.boylam.text="Boylam : Alınamadı"
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        izin=ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)
        if(requestCode==100){
            if(grantResults.size>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                locationTask=flpc.lastLocation
                konumAl()
            }
        }else{

        }
    }
}