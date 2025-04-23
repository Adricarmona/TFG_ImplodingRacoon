namespace implodingRacoon.Models.Database.Dto
{
    public class UsersInLobby
    {
        public int IdSala { get; set; } // id de la sala
        public int IdHost { get; set; } // id del host de la mesa
        public int IdUsuario1 { get; set; } = 0; // id del usuario 1
        public int IdUsuario2 { get; set; } = 0; // id del usuario 2
        public int IdUsuario3 { get; set; } = 0; // id del usuario 3
        public int IdUsuario4 { get; set; } = 0; // id del usuario 4
        public int IdUsuario5 { get; set; } = 0; // id del usuario 5
    }
}
