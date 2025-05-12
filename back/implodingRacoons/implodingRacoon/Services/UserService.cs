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
                cantidadAmigos = user.idAmigos.Count(),
            };
        }

        public async Task<List<UserAmigos>> GetFriendsByUserIdAsync(int id)
        {
            var user = await _unitOfWork.UsuarioRepository.GetUserByIdAndFriends(id);

            if (user == null)
                return null;

            var friends = new List<UserAmigos>();
            foreach (int friendId in user.idAmigos)
            {

                var friend = await _unitOfWork.UsuarioRepository.GetByIdAsync(friendId);
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
            
            if (!user.idAmigos.Contains(friend.Id))
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
            
            var request = user.SolicitudesRecibidas.FirstOrDefault(r => r.UsuarioEnviaId == friendId);
            if (request != null)
            {
                user.SolicitudesRecibidas.Remove(request);
                user.idAmigos.Add(friend.Id);

                friend.SolicitudesEnviadas.Remove(request);
                friend.idAmigos.Add(user.Id);
                
                await _unitOfWork.SaveAsync();

                return true;
            }
            else
            { 
                return false;
            }


        }
    }
}
