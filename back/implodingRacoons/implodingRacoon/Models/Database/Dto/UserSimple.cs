namespace implodingRacoon.Models.Database.Dto
{
    public class UserSimple
    {
        public int Id { get; set; }
        public string NombreUsuario { get; set; }
        public string Correo { get; set; }
        public string Foto { get; set; }
        public bool Conectado { get; set; }
        public bool Admin { get; set; }
    }
}
