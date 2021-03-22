package com.example.viewandlayout

import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import kotlinx.parcelize.Parcelize

data class FormState(
    val valid: Boolean,
    val message: String,
    val buttonValid: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readBoolean(),
        parcel.readString().orEmpty(),
        parcel.readBoolean()
    ) {
    }

    fun validateTrue(): FormState {
        return copy(valid = true)
    }

    fun validateFalse(): FormState {
        return copy(valid = false)
    }

    fun errorMessage(error: String): FormState {
        return copy(message = error )
    }

    fun validateButton(boolState: Boolean): FormState {
        return copy(buttonValid = boolState)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeBoolean(valid)
        parcel.writeString(message)
        parcel.writeBoolean(buttonValid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FormState> {
        override fun createFromParcel(parcel: Parcel): FormState {
            return FormState(parcel)
        }

        override fun newArray(size: Int): Array<FormState?> {
            return arrayOfNulls(size)
        }
    }
}

