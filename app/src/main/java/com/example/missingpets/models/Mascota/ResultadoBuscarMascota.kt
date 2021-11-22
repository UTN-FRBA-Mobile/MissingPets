import com.example.missingpets.network.Mascota

data class ResultadoBuscarMascota(
    val exitoso: Mascota? = null,
    val error: String? = null
)
