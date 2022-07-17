package ve.com.teeac.mynewapplication.domain.models

data class Thumbnail(
    val extension: String,
    val path: String
) {
    fun getUrl(): String = "${this.path}/portrait_xlarge.${this.extension}"
}
