using System.ComponentModel.DataAnnotations;

namespace implodingRacoon.Models.Database.Entities
{
    public class Usuario
    {
        [Key]
        public int Id { get; set; }
        public string NombreUsuario { get; set; }
        public string Correo { get; set; }
        public string Contrasena { get; set; }
        public string Foto { get; set; }
        public bool Conectado { get; set; }

        public ICollection<SolicitudAmistad> SolicitudesAmistad { get; set; }
        public ICollection<Publicacion> Publicaciones { get; set; }
        public ICollection<Comentario> Comentarios { get; set; }
        public ICollection<UsuarioHistorial> HistorialPartidas { get; set; }
    }
}
