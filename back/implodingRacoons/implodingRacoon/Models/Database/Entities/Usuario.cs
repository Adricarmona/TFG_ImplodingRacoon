using System.ComponentModel.DataAnnotations;
using System.Collections.Generic;

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
        public bool Admin { get; set; }


        // relacion de amistad
        public ICollection<Amistad> Amigos { get; set; } = new List<Amistad>();


        // solicitudes de amistad
        public ICollection<SolicitudAmistad>? SolicitudesEnviadas { get; set; }
        public ICollection<SolicitudAmistad>? SolicitudesRecibidas { get; set; }


        // Wiki
        public ICollection<Publicacion> Publicaciones { get; set; } = new List<Publicacion>();
        public ICollection<Comentario> Comentarios { get; set; } = new List<Comentario>();


        // Historial partidas
        public ICollection<UsuarioHistorial> HistorialPartidas { get; set; } = new List<UsuarioHistorial>();
    }
}
