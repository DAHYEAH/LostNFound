package com.example.lostnfound.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.lostnfound.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class GoogleMapActivity extends AppCompatActivity {
    //로그캣 사용 설정
    private static final String TAG = "GoogleMapActivity";

    //객체 선언
    SupportMapFragment mapFragment;
    GoogleMap map;
    Button btnLocation, btnKor2Loc;
    EditText editText;

    MarkerOptions myMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);

        //권한 설정
        checkDangerousPermissions();

        //객체 초기화
        editText = findViewById(R.id.editText);
        btnLocation = findViewById(R.id.button1);
        btnKor2Loc = findViewById(R.id.button2);

        //지도 프래그먼트 설정
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Log.d(TAG, "onMapReady: ");
                map = googleMap;
                if (ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                map.setMyLocationEnabled(true);
                LatLng curPoint = new LatLng(36.350789, 127.300485);
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));

                if(myMarker == null) {

                    int height = 50;
                    int width = 50;
                    BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.searchpic);
                    Bitmap b = bitmapdraw.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

                    myMarker = new MarkerOptions();
                    myMarker.position(new LatLng(36.350789, 127.300485));
                    myMarker.title("◎ 내위치\n");
                    myMarker.snippet("여기가 어디지?");
                    myMarker.draggable(true);
//                    myMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.searchpic));
//                            myMarker.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));

                    Log.e("위치!", String.valueOf(myMarker.getPosition().latitude) + String.valueOf(myMarker.getPosition().longitude));
                    map.addMarker(myMarker);

                }
                setMarkerDragListener(map);
            }

        });
        MapsInitializer.initialize(this);

        //위치 확인 버튼 기능 추가
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestMyLocation();
//                showCurrentLocation(location);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("loc_lat",String.valueOf(myMarker.getPosition().latitude));
                resultIntent.putExtra("loc_lng",String.valueOf(myMarker.getPosition().longitude));
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

        btnKor2Loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().length() > 0) {
                    Location location = getLocationFromAddress(getApplicationContext(), editText.getText().toString());

                    showCurrentLocation(location);

                }
            }
        });
    }

    private Location getLocationFromAddress(Context context, String address) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses;
        Location resLocation = new Location("");
        try {
            addresses = geocoder.getFromLocationName(address, 5);
            if((addresses == null) || (addresses.size() == 0)) {
                return null;
            }
            Address addressLoc = addresses.get(0);

            resLocation.setLatitude(addressLoc.getLatitude());
            resLocation.setLongitude(addressLoc.getLongitude());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resLocation;
    }

    private void requestMyLocation() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            long minTime = 1000;    //갱신 시간
            float minDistance = 0;  //갱신에 필요한 최소 거리

            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, new android.location.LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    showCurrentLocation(location);
                    Log.e("바뀜","changed");
                }
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                    LocationListener.super.onStatusChanged(provider, status, extras);
                }

                @Override
                public void onProviderDisabled(@NonNull String provider) {
                    LocationListener.super.onProviderDisabled(provider);
                }

                @Override
                public void onProviderEnabled(@NonNull String provider) {
                    LocationListener.super.onProviderEnabled(provider);
                }
            });
//            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, new LocationListener() {
//                @Override
//                public void onLocationChanged(Location location) {
//                    showCurrentLocation(location);
//                }
//
//
//            });
        }
        catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void showCurrentLocation(Location location) {
        LatLng curPoint = new LatLng(location.getLatitude(), location.getLongitude());
        String msg = "Latitutde : " + curPoint.latitude
                + "\nLongitude : " + curPoint.longitude;

        //화면 확대, 숫자가 클수록 확대
        map.getUiSettings().setZoomControlsEnabled(true);
//        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));

        //마커 찍기
        Location targetLocation = new Location("");
//        targetLocation.setLatitude(36.350789);
//        targetLocation.setLongitude(127.300485);
        targetLocation.setLatitude(curPoint.latitude);
        targetLocation.setLongitude(curPoint.longitude);
        showMyMarker(targetLocation);

    }

    //------------------권한 설정 시작------------------------
    private void checkDangerousPermissions() {
        String[] permissions = {
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_WIFI_STATE
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    //------------------권한 설정 끝------------------------

    private void showMyMarker(Location location) {
        if(myMarker == null) {

            int height = 50;
            int width = 50;
            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.searchpic);
            Bitmap b = bitmapdraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

            myMarker = new MarkerOptions();
            myMarker.position(new LatLng(location.getLatitude(), location.getLongitude()));
            myMarker.title("◎ 내위치\n");
            myMarker.snippet("여기가 어디지?");
            myMarker.draggable(true);
//            myMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.searchpic));
//            myMarker.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
            Log.e("위치", String.valueOf(myMarker.getPosition().latitude) + String.valueOf(myMarker.getPosition().longitude));
            map.addMarker(myMarker);
        }
    }
    private void setMarkerDragListener(GoogleMap map) {
        map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

            @Override
            public void onMarkerDragStart(Marker marker) {
            }

            @Override
            public void onMarkerDrag(Marker marker) {
//                LatLng p = marker.getPosition();
//                String msg = "Latitutde : " + p.latitude
//                        + "\nLongitude : " + p.longitude;
//                Toast.makeText(getApplication(), msg, Toast.LENGTH_SHORT).show();
//                updateInfo(String.format(Locale.US, "drag %.1f %.1f", p.latitude, p.longitude), marker);
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                LatLng p = marker.getPosition();
                String msg = "eLatitutde : " + p.latitude
                        + "\neLongitude : " + p.longitude;
                myMarker.position(new LatLng(p.latitude, p.longitude));
//                updateInfo("drag end", marker);
            }
        });
    }
}