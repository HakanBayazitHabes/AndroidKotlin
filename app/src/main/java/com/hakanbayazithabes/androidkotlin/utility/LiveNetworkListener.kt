package com.hakanbayazithabes.androidkotlin.utility

import android.content.Context
import android.net.ConnectivityManager

class LiveNetworkListener {

    companion object {
        private fun getConnectionType(context: Context): Int {
            var result = 0

            //cm nesnesi , ağ bağlantısı durumunu kontrol etmek, ağ bilgilerini almak ve diğer ağ işlemlerini
            // gerçekleştirmek için kullanılabilir. Bağlantı yöneticisi, mobil veri bağlantısı, Wi-Fi bağlantısı,
            // Bluetooth bağlantısı gibi çeşitli bağlantı türlerini yönetir.
            // Context.CONNECTIVITY_SERVICE, bağlantı yöneticisi hizmetine erişmek için kullanılan bir sabittir.
            //as ConnectivityManager? . Bu, bağlantı yöneticisi hizmetinin işlevlerine erişmek için gereklidir.
            var cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

            cm.run {
                this?.getNetworkCapabilities(this?.activeNetwork).run {
                    if (this != null) {
                        if (this.hasTransport(android.net.NetworkCapabilities.TRANSPORT_WIFI)) {
                            result = 1
                        } else if (this.hasTransport(android.net.NetworkCapabilities.TRANSPORT_CELLULAR)) {
                            result = 2
                        } else if (this.hasTransport(android.net.NetworkCapabilities.TRANSPORT_VPN)) {
                            result = 3
                        }
                    }
                }
            }

            return result
        }

        fun isConencted(): Boolean {
            var context = GlobalApp.getContext()

            var result = getConnectionType(context)

            return result != 0
        }
    }
}