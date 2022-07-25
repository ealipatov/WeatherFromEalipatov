package by.ealipatov.kotlin.weatherfromealipatov.model.geo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GeoObjectCollection(
    val featureMember: List<FeatureMember>,
    val metaDataProperty: MetaDataPropertyX
): Parcelable