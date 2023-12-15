package com.example.neweco

import android.Manifest
import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.neweco.fragments.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.location.FilteringMode
import com.yandex.mapkit.location.LocationStatus
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), CameraListener {
    private lateinit var SplashScreenFragment : SplashScreenFragment
    private lateinit var RegistrationFragment: RegistrationFragment
    private lateinit var LoginFragment: LoginFragment
    private lateinit var BarcodeScannerFragment : BarcodeScannerFragment
    private lateinit var MyProfileFragment: MyProfileFragment
    private lateinit var BonusFragment: BonusFragment
    private lateinit var MarketFragment : MarketFragment
    lateinit var mapview : MapView
    private lateinit var drawerLayout: DrawerLayout
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var isCameraMoving = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey("98462973-7c03-4c44-a022-5681f73c4e98")
        MapKitFactory.initialize(this)
        setContentView(R.layout.activity_main)
        SplashScreenFragment = SplashScreenFragment()
        RegistrationFragment = RegistrationFragment()
        LoginFragment = LoginFragment()
        BarcodeScannerFragment = BarcodeScannerFragment()
        MyProfileFragment = MyProfileFragment()
        BonusFragment = BonusFragment()
        MarketFragment = MarketFragment()

        setFragmentSplashScreen(SplashScreenFragment)
        val sharedPreferences : SharedPreferences = this.getSharedPreferences("ecoSharedPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val mapkit : MapKit = MapKitFactory.getInstance()

        mapview = findViewById(R.id.mapkit)
        mapview.map.addCameraListener(this)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)


        val task = fusedLocationProviderClient.lastLocation
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 0)
            return
        }
        task.addOnSuccessListener{
            if(it != null){

                var locationmap = mapkit.createUserLocationLayer(mapview.mapWindow)
                locationmap.isVisible = true
                mapview.map.move(
                    // zoom was 17f, changed for testing
                    CameraPosition(Point("${it.latitude}".toDouble(), "${it.longitude}".toDouble()), 17.0f, 0.0f, 0.0f),
                    Animation(Animation.Type.SMOOTH, 5f),null)
                Log.d("hello", "${it.latitude}\".toDouble(), \"${it.longitude}")
            }else{
                Log.d("hello", "so bad")
            }

        }

        mygeo.setOnClickListener {
            task.addOnSuccessListener{
                if(it != null){

                    mapview.map.move(
                        // zoom was 17f, changed for testing
                        CameraPosition(Point("${it.latitude}".toDouble(), "${it.longitude}".toDouble()), 17.0f, 0.0f, 0.0f),
                        Animation(Animation.Type.SMOOTH, 5f),null)
                    Log.d("hello", "${it.latitude}\".toDouble(), \"${it.longitude}")
                }else{
                    Log.d("hello", "so bad")
                }

            }
        }


        //mapview.map.addCameraListener(this)

//        val points = java.util.ArrayList<Point>()
//        points.add(Point(59.949911, 30.316560))
//        points.add(Point(59.949121, 30.316008))
//        points.add(Point(59.949441, 30.318132))
//        points.add(Point(59.950075, 30.316915))
//        points.add(Point(59.949911, 30.316560))
//        val polygon = Polygon(LinearRing(points), ArrayList())
//        val polygonMapObject: PolygonMapObject = mapview.getMap().getMapObjects().addPolygon(polygon)
//        polygonMapObject.fillColor = 0x3300FF00
//        polygonMapObject.strokeWidth = 3.0f
//        polygonMapObject.strokeColor = Color.GREEN



        val mark: PlacemarkMapObject = mapview.getMap().getMapObjects().addPlacemark(Point("60.0054".toDouble(), "30.2973".toDouble()))
        mark.setIcon(ImageProvider.fromResource(this, R.drawable.renta))
        //mark.opacity = 0.5
        mark.isDraggable = true
        mark.addTapListener(circleMapObjectTapListenerRENT)
        //mapview.map.mapObjects.addPlacemark(Point("60.0054".toDouble(), "30.2973".toDouble()))


        val mark2: PlacemarkMapObject = mapview.getMap().getMapObjects().addPlacemark(Point("59.941650".toDouble(), "30.276447".toDouble()))
        //mark.opacity = 0.5f
        mark2.setIcon(ImageProvider.fromResource(this, R.drawable.batery222))
        mark2.isDraggable = true
        mark2.addTapListener(circleMapObjectTapListenerBATARY)
        //mapview.map.mapObjects.addPlacemark(Point("59.941650".toDouble(), "30.276447".toDouble()))



        val mark3: PlacemarkMapObject = mapview.getMap().getMapObjects().addPlacemark(Point("59.959576".toDouble(), "30.317003".toDouble()))
        //mark.opacity = 0.5f
        mark3.setIcon(ImageProvider.fromResource(this, R.drawable.batery222))
        mark3.isDraggable = true
        mark3.addTapListener(circleMapObjectTapListenerBATARY)
        //mapview.map.mapObjects.addPlacemark(Point("59.959576".toDouble(), "30.317003".toDouble()))



        val mark4: PlacemarkMapObject = mapview.getMap().getMapObjects().addPlacemark(Point("59.912499".toDouble(), "30.319582".toDouble()))
        //mark.opacity = 0.5f
        mark4.setIcon(ImageProvider.fromResource(this, R.drawable.batery222))
        mark4.isDraggable = true
        mark4.addTapListener(circleMapObjectTapListenerBATARY)
        //mapview.map.mapObjects.addPlacemark(Point("59.912499".toDouble(), "30.319582".toDouble()))



        val mark5: PlacemarkMapObject = mapview.getMap().getMapObjects().addPlacemark(Point("59.864353".toDouble(), "30.309617".toDouble()))
        //mark.opacity = 0.5f
        mark5.setIcon(ImageProvider.fromResource(this, R.drawable.batery222))
        mark5.isDraggable = true
        mark5.addTapListener(circleMapObjectTapListenerBATARY)
        //mapview.map.mapObjects.addPlacemark(Point("59.864353".toDouble(), "30.309617".toDouble()))



        val mark6: PlacemarkMapObject = mapview.getMap().getMapObjects().addPlacemark(Point("59.858288".toDouble(), "30.195890".toDouble()))
        //mark.opacity = 0.5f
        mark6.setIcon(ImageProvider.fromResource(this, R.drawable.batery222))
        mark6.isDraggable = true
        mark6.addTapListener(circleMapObjectTapListenerBATARY)
        //mapview.map.mapObjects.addPlacemark(Point("59.858288".toDouble(), "30.195890".toDouble()))

        val markMETAL2: PlacemarkMapObject = mapview.getMap().getMapObjects().addPlacemark(Point("59.952872".toDouble(), "30.264894".toDouble()))
        //mark.opacity = 0.5f
        markMETAL2.setIcon(ImageProvider.fromResource(this, R.drawable.metela))
        markMETAL2.isDraggable = true
        markMETAL2.addTapListener(circleMapObjectTapListenerMETAL)
        //mapview.map.mapObjects.addPlacemark(Point("59.952872".toDouble(), "30.264894".toDouble()))



        val markMETAL3: PlacemarkMapObject = mapview.getMap().getMapObjects().addPlacemark(Point("59.960442".toDouble(), "30.283195".toDouble()))
        //mark.opacity = 0.5f
        markMETAL3.setIcon(ImageProvider.fromResource(this, R.drawable.metela))
        markMETAL3.isDraggable = true
        markMETAL3.addTapListener(circleMapObjectTapListenerMETAL)
        //mapview.map.mapObjects.addPlacemark(Point("59.960442".toDouble(), "30.283195".toDouble()))



        val markMETAL4: PlacemarkMapObject = mapview.getMap().getMapObjects().addPlacemark(Point("59.968361".toDouble(), "30.305976".toDouble()))
        //mark.opacity = 0.5f
        markMETAL4.setIcon(ImageProvider.fromResource(this, R.drawable.metela))
        markMETAL4.isDraggable = true
        markMETAL4.addTapListener(circleMapObjectTapListenerMETAL)
        //mapview.map.mapObjects.addPlacemark(Point("59.968361".toDouble(), "30.305976".toDouble()))



        val markMETAL5: PlacemarkMapObject = mapview.getMap().getMapObjects().addPlacemark(Point("59.987737".toDouble(), "30.278293".toDouble()))
        //mark.opacity = 0.5f
        markMETAL5.setIcon(ImageProvider.fromResource(this, R.drawable.metela))
        markMETAL5.isDraggable = true
        markMETAL5.addTapListener(circleMapObjectTapListenerMETAL)
        //mapview.map.mapObjects.addPlacemark(Point("59.987737".toDouble(), "30.278293".toDouble()))



        val markMETAL6: PlacemarkMapObject = mapview.getMap().getMapObjects().addPlacemark(Point("59.991964".toDouble(), "30.358326".toDouble()))
        //mark.opacity = 0.5f
        markMETAL6.setIcon(ImageProvider.fromResource(this, R.drawable.metela))
        markMETAL6.isDraggable = true
        markMETAL6.addTapListener(circleMapObjectTapListenerMETAL)
        //mapview.map.mapObjects.addPlacemark(Point("59.991964".toDouble(), "30.358326".toDouble()))


        val markSHIRT2: PlacemarkMapObject = mapview.getMap().getMapObjects().addPlacemark(Point("59.925883".toDouble(), "30.326773".toDouble()))
        //mark.opacity = 0.5f
        markSHIRT2.setIcon(ImageProvider.fromResource(this, R.drawable.shirta))
        markSHIRT2.isDraggable = true
        markSHIRT2.addTapListener(circleMapObjectTapListenerSHIRT)
        //mapview.map.mapObjects.addPlacemark(Point("59.925883".toDouble(), "30.326773".toDouble()))



        val markSHIRT3: PlacemarkMapObject = mapview.getMap().getMapObjects().addPlacemark(Point("59.969861".toDouble(), "30.385455".toDouble()))
        //mark.opacity = 0.5f
        markSHIRT3.setIcon(ImageProvider.fromResource(this, R.drawable.shirta))
        markSHIRT3.isDraggable = true
        markSHIRT3.addTapListener(circleMapObjectTapListenerSHIRT)
        //mapview.map.mapObjects.addPlacemark(Point("59.969861".toDouble(), "30.385455".toDouble()))



        val markSHIRT4: PlacemarkMapObject = mapview.getMap().getMapObjects().addPlacemark(Point("59.899249".toDouble(), "30.273472".toDouble()))
        //mark.opacity = 0.5f
        markSHIRT4.setIcon(ImageProvider.fromResource(this, R.drawable.shirta))
        markSHIRT4.isDraggable = true
        markSHIRT4.addTapListener(circleMapObjectTapListenerSHIRT)
        //mapview.map.mapObjects.addPlacemark(Point("59.899249".toDouble(), "30.273472".toDouble()))



        val markSHIRT5: PlacemarkMapObject = mapview.getMap().getMapObjects().addPlacemark(Point("59.910412".toDouble(), "30.307721".toDouble()))
        //mark.opacity = 0.5f
        markSHIRT5.setIcon(ImageProvider.fromResource(this, R.drawable.shirta))
        markSHIRT5.isDraggable = true
        markSHIRT5.addTapListener(circleMapObjectTapListenerSHIRT)
        //mapview.map.mapObjects.addPlacemark(Point("59.910412".toDouble(), "30.307721".toDouble()))



        val markSHIRT6: PlacemarkMapObject = mapview.getMap().getMapObjects().addPlacemark(Point("59.958906".toDouble(), "30.288472".toDouble()))
        //mark.opacity = 0.5f
        markSHIRT6.setIcon(ImageProvider.fromResource(this, R.drawable.shirta))
        markSHIRT6.isDraggable = true
        markSHIRT6.addTapListener(circleMapObjectTapListenerSHIRT)
        //mapview.map.mapObjects.addPlacemark(Point("59.958906".toDouble(), "30.288472".toDouble()))


        val markRENT2: PlacemarkMapObject = mapview.getMap().getMapObjects().addPlacemark(Point("59.969407".toDouble(), "30.246404".toDouble()))
        //mark.opacity = 0.5f
        markRENT2.setIcon(ImageProvider.fromResource(this, R.drawable.renta))
        markRENT2.isDraggable = true
        markRENT2.addTapListener(circleMapObjectTapListenerRENT)
        //mapview.map.mapObjects.addPlacemark(Point("59.969407".toDouble(), "30.246404".toDouble()))



        val markRENT3: PlacemarkMapObject = mapview.getMap().getMapObjects().addPlacemark(Point("59.986068".toDouble(), "30.205245".toDouble()))
        //mark.opacity = 0.5f
        markRENT3.setIcon(ImageProvider.fromResource(this, R.drawable.renta))
        markRENT3.isDraggable = true
        markRENT3.addTapListener(circleMapObjectTapListenerRENT)
        //mapview.map.mapObjects.addPlacemark(Point("59.986068".toDouble(), "30.205245".toDouble()))



        val markRENT4: PlacemarkMapObject = mapview.getMap().getMapObjects().addPlacemark(Point("59.929637".toDouble(), "30.359981".toDouble()))
        //mark.opacity = 0.5f
        markRENT4.setIcon(ImageProvider.fromResource(this, R.drawable.renta))
        markRENT4.isDraggable = true
        markRENT4.addTapListener(circleMapObjectTapListenerRENT)
        //mapview.map.mapObjects.addPlacemark(Point("59.929637".toDouble(), "30.359981".toDouble()))



        val markRENT5: PlacemarkMapObject = mapview.getMap().getMapObjects().addPlacemark(Point("59.949295".toDouble(), "30.234145".toDouble()))
        //mark.opacity = 0.5f
        markRENT5.setIcon(ImageProvider.fromResource(this, R.drawable.renta))
        markRENT5.isDraggable = true
        markRENT5.addTapListener(circleMapObjectTapListenerRENT)
        //mapview.map.mapObjects.addPlacemark(Point("59.949295".toDouble(), "30.234145".toDouble()))



        val markRENT6: PlacemarkMapObject = mapview.getMap().getMapObjects().addPlacemark(Point("59.858710".toDouble(), "30.248665".toDouble()))
        //mark.opacity = 0.5f
        markRENT6.setIcon(ImageProvider.fromResource(this, R.drawable.renta))
        markRENT6.isDraggable = true
        markRENT6.addTapListener(circleMapObjectTapListenerRENT)
        //mapview.map.mapObjects.addPlacemark(Point("59.858710".toDouble(), "30.248665".toDouble()))



        val markPARK2: PlacemarkMapObject = mapview.getMap().getMapObjects().addPlacemark(Point("59.970104".toDouble(), "30.246167".toDouble()))
//mark.opacity = 0.5f
        markPARK2.setIcon(ImageProvider.fromResource(this, R.drawable.parka))
        markPARK2.isDraggable = true
        markPARK2.addTapListener(circleMapObjectTapListenerPARK)
        //mapview.map.mapObjects.addPlacemark(Point("59.970104".toDouble(), "30.246167".toDouble()))



        val markPARK3: PlacemarkMapObject = mapview.getMap().getMapObjects().addPlacemark(Point("59.942922".toDouble(), "30.332523".toDouble()))
//mark.opacity = 0.5f
        markPARK3.setIcon(ImageProvider.fromResource(this, R.drawable.parka))
        markPARK3.isDraggable = true
        markPARK3.addTapListener(circleMapObjectTapListenerPARK)
        //mapview.map.mapObjects.addPlacemark(Point("59.942922".toDouble(), "30.332523".toDouble()))



        val markPARK4: PlacemarkMapObject = mapview.getMap().getMapObjects().addPlacemark(Point("59.902452".toDouble(), "30.262244".toDouble()))
//mark.opacity = 0.5f
        markPARK4.setIcon(ImageProvider.fromResource(this, R.drawable.parka))
        markPARK4.isDraggable = true
        markPARK4.addTapListener(circleMapObjectTapListenerPARK)
        //mapview.map.mapObjects.addPlacemark(Point("59.902452".toDouble(), "30.262244".toDouble()))



        val markPARK5: PlacemarkMapObject = mapview.getMap().getMapObjects().addPlacemark(Point("59.969298".toDouble(), "30.326093".toDouble()))
//mark.opacity = 0.5f
        markPARK5.setIcon(ImageProvider.fromResource(this, R.drawable.parka))
        markPARK5.isDraggable = true
        markPARK5.addTapListener(circleMapObjectTapListenerPARK)
        //mapview.map.mapObjects.addPlacemark(Point("59.969298".toDouble(), "30.326093".toDouble()))



        val markPARK6: PlacemarkMapObject = mapview.getMap().getMapObjects().addPlacemark(Point("59.890913".toDouble(), "30.276254".toDouble()))
//mark.opacity = 0.5f
        markPARK6.setIcon(ImageProvider.fromResource(this, R.drawable.parka))
        markPARK6.isDraggable = true
        markPARK6.addTapListener(circleMapObjectTapListenerPARK)
        //mapview.map.mapObjects.addPlacemark(Point("59.890913".toDouble(), "30.276254".toDouble()))






        object : CountDownTimer(3000, 1000) {

            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                val transition: Transition = Slide(Gravity.TOP)
                transition.duration = 300
                transition.addTarget(splashscreen)

                TransitionManager.beginDelayedTransition(main, transition)
                splashscreen.visibility = View.GONE
                var getlog = sharedPreferences.getString("log", "none")
                if (getlog != "yes"){
                    reglog.visibility = View.VISIBLE
                    setFragmentReglog(LoginFragment)

                }else{
                    testframe.visibility = View.VISIBLE
                    expandableView.visibility = View.VISIBLE
                    btnOpenDrawer.visibility = View.VISIBLE
                }
            }
        }.start()


        drawerLayout = findViewById(R.id.drawerLayout)


        // Инициализация ActionBarDrawerToggle для управления состоянием бокового меню
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Обработка нажатия на кнопку для открытия бокового меню
        btnOpenDrawer.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        var getnameformenu = sharedPreferences.getString("name", "none")
        var getmailformenu = sharedPreferences.getString("mail", "none")
        nameinmenu.text = getnameformenu.toString()
        emailinmenu.text = getmailformenu.toString()

        scan3.setOnClickListener {
            setAllFragment(BarcodeScannerFragment)
            allframes.visibility = View.VISIBLE
            allframes.translationY = allframes.height.toFloat()

            val slideUpAnimation = ObjectAnimator.ofFloat(allframes, "translationY", allframes.height.toFloat(), 0f)
            slideUpAnimation.duration = 300 // Установите желаемую продолжительность анимации
            slideUpAnimation.start()

        }

        gomarket.setOnClickListener {

            setAllFragment(MarketFragment)
            allframes.visibility = View.VISIBLE
            allframes.translationY = allframes.height.toFloat()

            val slideUpAnimation = ObjectAnimator.ofFloat(allframes, "translationY", allframes.height.toFloat(), 0f)
            slideUpAnimation.duration = 300 // Установите желаемую продолжительность анимации
            slideUpAnimation.start()


        }


        taxi2.setOnClickListener{
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://ecotaxi.io/"))
            startActivity(i)
        }

        rent.setOnClickListener{
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://whoosh-bike.ru/#russiapopup"))
            startActivity(i)
        }

        profile.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            setAllFragment(MyProfileFragment)
            allframes.visibility = View.VISIBLE
            allframes.translationY = allframes.height.toFloat()

            val slideUpAnimation = ObjectAnimator.ofFloat(allframes, "translationY", allframes.height.toFloat(), 0f)
            slideUpAnimation.duration = 300 // Установите желаемую продолжительность анимации
            slideUpAnimation.start()
        }
        bonus.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            setAllFragment(BonusFragment)
            allframes.visibility = View.VISIBLE
            allframes.translationY = allframes.height.toFloat()

            val slideUpAnimation = ObjectAnimator.ofFloat(allframes, "translationY", allframes.height.toFloat(), 0f)
            slideUpAnimation.duration = 300 // Установите желаемую продолжительность анимации
            slideUpAnimation.start()
        }
    }



    private val circleMapObjectTapListener =
        MapObjectTapListener { mapObject, point ->
            imageViewBAR1.setImageResource(R.drawable.batary)
            imageViewBAR1.visibility = View.VISIBLE
            imageViewBARBACK.visibility = View.VISIBLE
            imageViewBARBACK.setOnClickListener {
                imageViewBAR1.visibility = View.INVISIBLE
                imageViewBARBACK.visibility = View.INVISIBLE
            }
            true
        }


    private val circleMapObjectTapListenerBATARY =
        MapObjectTapListener { mapObject, point ->
            imageViewBAR1.setImageResource(R.drawable.batary)
            imageViewBAR1.visibility = View.VISIBLE
            //Log.d("check", "right")
            imageViewBARBACK.visibility = View.VISIBLE
            imageViewBARBACK.setOnClickListener {
                imageViewBAR1.visibility = View.INVISIBLE
                imageViewBARBACK.visibility = View.INVISIBLE
            }
            true
        }

    private val circleMapObjectTapListenerMETAL =
        MapObjectTapListener { mapObject, point ->
            imageViewBAR1.setImageResource(R.drawable.metal)
            imageViewBAR1.visibility = View.VISIBLE
            imageViewBARBACK.visibility = View.VISIBLE
            imageViewBARBACK.setOnClickListener {
                imageViewBAR1.visibility = View.INVISIBLE
                imageViewBARBACK.visibility = View.INVISIBLE
            }
            true
        }

    private val circleMapObjectTapListenerSHIRT =
        MapObjectTapListener { mapObject, point ->
            imageViewBAR1.setImageResource(R.drawable.wearin)
            imageViewBAR1.visibility = View.VISIBLE
            imageViewBARBACK.visibility = View.VISIBLE
            imageViewBARBACK.setOnClickListener {
                imageViewBAR1.visibility = View.INVISIBLE
                imageViewBARBACK.visibility = View.INVISIBLE
            }
            true
        }


    private val circleMapObjectTapListenerRENT =
        MapObjectTapListener { mapObject, point ->
            imageViewBAR1.setImageResource(R.drawable.arens)
            imageViewBAR1.visibility = View.VISIBLE
            imageViewBARBACK.visibility = View.VISIBLE
            imageViewBARBACK.setOnClickListener {
                imageViewBAR1.visibility = View.INVISIBLE
                imageViewBARBACK.visibility = View.INVISIBLE
            }
            true
        }

    private val circleMapObjectTapListenerPARK =
        MapObjectTapListener { mapObject, point ->
            imageViewBAR1.setImageResource(R.drawable.parks)
            imageViewBAR1.visibility = View.VISIBLE
            imageViewBARBACK.visibility = View.VISIBLE
            imageViewBARBACK.setOnClickListener {
                imageViewBAR1.visibility = View.INVISIBLE
                imageViewBARBACK.visibility = View.INVISIBLE
            }
            true
        }



    override fun onCameraPositionChanged(
        map: Map,
        cameraPosition: CameraPosition,
        cameraUpdateReason: CameraUpdateReason,
        finished: Boolean
    ) {
        expandableView.collapse()

        if (finished) { // Если камера закончила движение
            expandableView.expand()
        }
    }

    override fun onStop(){
        mapview.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }
    override fun onStart(){
        mapview.onStart()
        MapKitFactory.getInstance().onStart()
        super.onStart()
    }

    private fun createBitmapFromVector(art: Int): Bitmap? {
        val drawable = ContextCompat.getDrawable(this, art) ?: return null
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        ) ?: return null
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }



    private fun setAllFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        // Устанавливаем анимацию входа для фрагмента (появление снизу)


        fragmentTransaction.replace(R.id.allframes, fragment)
        fragmentTransaction.commit()
    }



    private fun setFragmentSplashScreen(fragment: Fragment){

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.splashscreen, fragment)
        fragmentTransaction.commit()
    }

    private fun setFragmentReglog(fragment: Fragment){

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.reglog, fragment)
        fragmentTransaction.commit()
    }

    fun makemapvisible() {
        testframe.visibility = View.VISIBLE
        expandableView.visibility = View.VISIBLE
        btnOpenDrawer.visibility = View.VISIBLE
    }

    fun makecamgone() {
        val slideDownAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down)

        allframes.startAnimation(slideDownAnimation)
        allframes.visibility = View.GONE

    }

    fun makemygeoinvis() {
        mygeo.visibility = View.GONE
    }
    fun makemygeovis() {

        mygeo.visibility = View.VISIBLE

    }


    fun makebackdarker() {
        animateVisibility(true)
    }

    fun makeliteagain() {
        animateVisibility(false)
    }

    private fun animateVisibility(shouldBeVisible: Boolean) {
        val startAlpha = if (shouldBeVisible) 0.0f else 1.0f
        val endAlpha = if (shouldBeVisible) 1.0f else 0.0f

        val animator = ObjectAnimator.ofFloat(backblack, "alpha", startAlpha, endAlpha)
        animator.duration = 500 // Длительность анимации в миллисекундах
        animator.interpolator = AccelerateDecelerateInterpolator()

        animator.addUpdateListener { animation ->
            val alpha = animation.animatedValue as Float
            backblack.alpha = alpha
        }

        animator.addListener(object : android.animation.AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                if (shouldBeVisible) {
                    backblack.visibility = View.VISIBLE
                }
            }

            override fun onAnimationEnd(animation: Animator) {
                if (!shouldBeVisible) {
                    backblack.visibility = View.GONE
                }
            }
        })

        animator.start()
    }

}

