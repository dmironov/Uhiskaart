package com.example.hiskaart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.hiskaart.R

class ValideeriFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_valideeri, container, false)

        // Find the ImageView and load the GIF using Glide
        val validatorGif = view.findViewById<ImageView>(R.id.nfc)
        Glide.with(this)
            .load(R.raw.nfc) // Load the GIF from raw resources
            .into(validatorGif)

        return view
    }
}