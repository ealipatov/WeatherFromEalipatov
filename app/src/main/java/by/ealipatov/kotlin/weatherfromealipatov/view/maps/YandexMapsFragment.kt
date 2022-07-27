package by.ealipatov.kotlin.weatherfromealipatov.view.maps

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.weatherfromealipatov.BuildConfig
import by.ealipatov.kotlin.weatherfromealipatov.R
import by.ealipatov.kotlin.weatherfromealipatov.databinding.FragmentYandexMapsBinding
import by.ealipatov.kotlin.weatherfromealipatov.databinding.FragmentYandexMapsUiBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import kotlinx.android.synthetic.main.fragment_yandex_maps.*

class YandexMapsFragment : Fragment() {

    private lateinit var yandexMap: MapView

    private val pointPiterburg = Point(59.945933, 30.320045)

    private var _binding: FragmentYandexMapsUiBinding? = null
    private val binding: FragmentYandexMapsUiBinding
        get() {
            return _binding!!
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
        MapKitFactory.initialize(requireContext())
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentYandexMapsUiBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        yandexMap = mapYandexView as MapView

        binding.buttonSearch.setOnClickListener {
            binding.searchAddress.text.toString().let { searchText ->
                val geocoder = Geocoder(requireContext())
                val result = geocoder.getFromLocationName(searchText, 1)
                val point = Point(result.first().latitude, result.first().longitude)
                addMarker(point)
                mapMoveToPoint(point)
            }
        }
    }

    fun addMarker (point: Point){
        val mapObjects = yandexMap.map.mapObjects.addCollection()
        val mark: PlacemarkMapObject = mapObjects.addPlacemark(point)
        mark.opacity = 0.5f
        mark.setIcon(ImageProvider.fromResource(requireContext(),R.drawable.ic_map_marker))
        mark.isDraggable = true
    }

    private fun mapMoveToPoint(point: Point) {
        yandexMap.map.move(
            CameraPosition(point, 14.0F, 0.0F, 0.0F),
            Animation(Animation.Type.SMOOTH, 5F),
            null
        )
    }

    override fun onStop() {
        super.onStop()
        yandexMap.onStop()
        MapKitFactory.getInstance().onStop()
    }

    override fun onStart() {
        super.onStart()
        yandexMap.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}