package com.example.neweco.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.neweco.MainActivity
import com.example.neweco.R
import kotlinx.android.synthetic.main.fragment_market.view.*
import java.util.ArrayList


class MarketFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_market, container, false)


        val imageList = ArrayList<SlideModel>() // Create image list


        imageList.add(SlideModel(R.drawable.testbanner))
        imageList.add(SlideModel(R.drawable.testbanner))
        imageList.add(SlideModel(R.drawable.testbanner))


        view.image_slider.setImageList(imageList)

        view.closemarket.setOnClickListener {

            (activity as MainActivity).makecamgone()

        }

        view.gobut1.setOnClickListener{
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://mergoo.ru/product/kasha-lnyanaya-kompas-zdorovya-zavarnaya-russkaya-400-g?variant_id=100618530&utm_source=yandex&utm_medium=cpc&utm_campaign=toveda&utm_content=12938239763&utm_term=&_openstat=ZGlyZWN0LnlhbmRleC5ydTs3OTc0OTE5NDsxMjkzODIzOTc2Mzt5YW5kZXgucnU6cHJlbWl1bQ&yclid=7649966437473452031"))
            startActivity(i)
        }
        view.gobut2.setOnClickListener{
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://4fresh.ru/products/zeln0011"))
            startActivity(i)
        }
        view.gobut3.setOnClickListener{
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://market.yandex.ru/product--pechene-bite-banan-bezgliutenovoe-125-g/1780844651?sku=650484566&wprid=1702679936068656-8226234761054744887-balancer-l7leveler-kubr-yp-vla-145-BAL-7456&utm_source_service=web&clid=703&src_pof=703&icookie=05U7L26fkwSsNRjC%2F1vZX3UBQ9Q9y%2FbBNaBrTdVZQgF4xgJhcY654tbjHeesQy1yoUA30T1q7fn2DPzfCdCd2Uabsew%3D&baobab_event_id=lq77oendoq"))
            startActivity(i)
        }
        view.gobut4.setOnClickListener{
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://flowwow.com/bakery-products/batonchiki-kak-budda-snikers/"))
            startActivity(i)
        }
        view.gobut5.setOnClickListener{
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://4fresh.ru/products/zeln0016"))
            startActivity(i)
        }
        view.gobut6.setOnClickListener{
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mi-ko.org/catalog/dlya_stirki/stiralnyy_poroshok_chistyy_kokos_1_kg/"))
            startActivity(i)
        }
        view.gobut7.setOnClickListener{
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://goldapple.ru/30023000007-pure-water"))
            startActivity(i)
        }
        view.gobut8.setOnClickListener{
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://goldapple.ru/brands/molecola"))
            startActivity(i)
        }
        view.gobut9.setOnClickListener{
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://pure-water.me/catalog/sredstva_dlya_uborki/konditsioner_opolaskivatel_dlya_belya_nezhnost/"))
            startActivity(i)
        }
        view.gobut10.setOnClickListener{
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://goldapple.ru/30014100001-universal-nyj"))
            startActivity(i)
        }


        return view
    }


}