package by.ealipatov.kotlin.weatherfromealipatov.view

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import by.ealipatov.kotlin.weatherfromealipatov.databinding.FragmentWebViewBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.stream.Collectors

class WebViewFragment: Fragment() {

    private var _binding: FragmentWebViewBinding? = null
    private val binding: FragmentWebViewBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebViewBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ok.setOnClickListener {
            binding.url.text.let { textURL ->
                val url = URL(textURL.toString())
                var myConnection: HttpURLConnection? = null
                val handler = Handler(Looper.myLooper()!!)

                myConnection = url.openConnection() as HttpURLConnection
                myConnection.connectTimeout = 5000

                Thread{
                    val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
                    val result = getLines(reader)

                    handler.post {
                            binding.webView.loadDataWithBaseURL(null,result,"text/html; charset=utf-8","utf-8",null)
                    }

                }.start()
            }
        }
    }

    //Функция из методички
    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String{
        return reader.lines().collect(Collectors.joining("\n"))
    }
}