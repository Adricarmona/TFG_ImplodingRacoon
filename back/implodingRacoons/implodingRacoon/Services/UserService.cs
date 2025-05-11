using System.Collections;
using implodingRacoon.Models.Database;
using implodingRacoon.Models.Database.Dto;
using implodingRacoon.Models.Database.Entities;

namespace implodingRacoon.Services
{
    public class UserService
    {
        private readonly UnitOfWork _unitOfWork;

        public UserService(UnitOfWork unitOfWork)
        {
            _unitOfWork = unitOfWork;
        }

        public async Task<UserPerfil> GetUserByIdAsync(int id)
        {
            var user = await _unitOfWork.UsuarioRepository.GetByIdAsync(id);
            
            if (user == null)
                return null;

            return new UserPerfil
            {
                Id = user.Id,
                NombreUsuario = user.NombreUsuario,
                urlFoto = user.Foto,
                cantidadAmigos = user.Amigos.Count(),
            };
        }

        public async Task<List<UserAmigos>> GetFriendsByUserIdAsync(int id)
        {
            var user = await _unitOfWork.UsuarioRepository.GetUserByIdAndFriends(id);

            if (user == null)
                return null;

            var friends = new List<UserAmigos>();
            foreach (var friendId in user.Amigos)
            {
                var friend = await _unitOfWork.UsuarioRepository.GetByIdAsync(friendId.AmigoId);
                if (friend != null)
                {
                    friends.Add(new UserAmigos
                    {
                        Id = friend.Id,
                        NombreUsuario = friend.NombreUsuario,
                        Foto = friend.Foto,
                    });
                }
            }

            return friends;
        }

        public async Task<bool> SetFriendRequest(int id, int friendId)
        {
            var user = await _unitOfWork.UsuarioRepository.GetUserByIdAndSolicitudes(id);
            var friend = await _unitOfWork.UsuarioRepository.GetUserByIdAndSolicitudes(friendId);

            if (user == null || friend == null)
                return false;

            var amigosUsuario = user.Amigos.Select(a => a.Amigo).ToList();

            if (!amigosUsuario.Contains(friend))
            {
                var solicitud = new SolicitudAmistad
                {
                    UsuarioRecibeId = friend.Id,
                    UsuarioEnviaId = user.Id,
                };

                user.SolicitudesEnviadas.Add(solicitud);
                friend.SolicitudesRecibidas.Add(solicitud);

                await _unitOfWork.SaveAsync();

                return true;
            }
            else
            {
                return false;
            }
        }

        public async Task<bool> AcceptFriendRequest(int id, int friendId)
        {
            var user = await _unitOfWork.UsuarioRepository.GetUserByIdAndSolicitudes(id);
            var friend = await _unitOfWork.UsuarioRepository.GetUserByIdAndSolicitudes(friendId);

            if (user == null || friend == null)
                return false;
            
            /*

            // las enviadas
            IList<SolicitudAmistad> solicitudesEnviadas = user.SolicitudesEnviadas.ToList();
            foreach (SolicitudAmistad solicitud in solicitudesEnviadas)
            {
                if (solicitud.UsuarioEnvia.Equals(friend) && solicitud.UsuarioRecibe.Equals(user) ||
                    solicitud.UsuarioRecibe.Equals(friend) && solicitud.UsuarioEnvia.Equals(user))
                {
                    solicitudesEnviadas.Remove(solicitud);
                    await _unitOfWork.SaveAsync();

                    // del usuarioq que lo solicita
                    var amigoUsuario = new Amistad
                    {
                        UsuarioId = user.Id,
                        AmigoId = friend.Id,
                        Amigo = friend
                    };

                    user.Amigos.Add(amigoUsuario);

                    var amigoUsuario2 = new Amistad
                    {
                        UsuarioId = friend.Id,
                        AmigoId = user.Id,
                        Amigo = user
                    };

                    friend.Amigos.Add(amigoUsuario2);

                    await _unitOfWork.SaveAsync();

                    return true;
                }
            }
            */

            // las recibidas
            IList<SolicitudAmistad> solicitudesRecibidas = user.SolicitudesRecibidas.ToList();
            SolicitudAmistad solicitudEscogida = null;
            
            foreach (SolicitudAmistad solicitud in solicitudesRecibidas)
            {
                if (solicitud.UsuarioEnvia.Id == friend.Id && solicitud.UsuarioRecibe.Id == user.Id ||
                    solicitud.UsuarioRecibe.Id == friend.Id && solicitud.UsuarioEnvia.Id == user.Id)
                {
                    solicitudEscogida = solicitud;
                }
            }

            if (solicitudEscogida != null)
            {
                user.SolicitudesRecibidas.Remove(solicitudEscogida);
                friend.SolicitudesEnviadas.Remove(solicitudEscogida);

                // del usuarioq que lo solicita
                var amigoUsuario = new Amistad
                {
                    UsuarioId = user.Id,
                    AmigoId = friend.Id,
                    Amigo = friend
                };

                user.Amigos.Add(amigoUsuario);

                var amigoUsuario2 = new Amistad
                {
                    UsuarioId = friend.Id,
                    AmigoId = user.Id,
                    Amigo = user
                };

                friend.Amigos.Add(amigoUsuario2);

                await _unitOfWork.SaveAsync();

                return true;
            }

            return false;

        }
    }
}
