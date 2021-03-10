package th.kaowkaow.pokedexandroid.Model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class PokemonModel(
    @SerializedName("cards")
    val cards: MutableList<Card> = mutableListOf()
): Parcelable

@Parcelize
data class Card(
    @SerializedName("attacks")
    val attacks: List<Attack> = listOf(),
    @SerializedName("hp")
    var hp: Int = 0,
    @SerializedName("id")
    val id: String = "",
    @SerializedName("imageUrl")
    val imageUrl: String = "",
    @SerializedName("isSelected")
    var isSelected: Boolean = false,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("rarity")
    var rarity: Int = 0,
    @SerializedName("weaknesses")
    val weaknesses: List<Weaknesse> = listOf()
): Parcelable

@Parcelize
data class Attack(
    @SerializedName("cost")
    val cost: List<String> = listOf(),
    @SerializedName("damage")
    var damage: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("text")
    val text: String = ""
): Parcelable

@Parcelize
data class Weaknesse(
    @SerializedName("type")
    val type: String = "",
    @SerializedName("value")
    var value: String = ""
): Parcelable