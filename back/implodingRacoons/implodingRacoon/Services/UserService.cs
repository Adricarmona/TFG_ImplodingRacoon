using implodingRacoon.Models.Database;
using implodingRacoon.Models.Database.Dto;
using implodingRacoon.Models.Database.Entities;
using Microsoft.AspNetCore.Http.HttpResults;
using Microsoft.AspNetCore.Mvc;

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


            var solicitudesUsuario = user.SolicitudesEnviadas;
            var solicitudesUsuarioRecibidas = user.SolicitudesRecibidas;
            var solicitudEnviada = false;

            foreach (var item in solicitudesUsuario)
            {
                if (item.UsuarioEnviaId == id && item.UsuarioRecibeId == friendId && solicitudEnviada != true)
                    solicitudEnviada = true;
            }

            foreach (var item in solicitudesUsuarioRecibidas)
            {
                if (item.UsuarioEnviaId == friendId && item.UsuarioRecibeId == id && solicitudEnviada != true)
                    solicitudEnviada = true;
            }

            if (!user.idAmigos.Contains(friend.Id) && solicitudEnviada == false)
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

        public async Task<List<UserAmigos>> GetFriendRequests(int id)
        {

            Usuario usuarios = await _unitOfWork.UsuarioRepository.GetUserByIdAndSolicitudes(id);
            
            if (usuarios == null)
                return null;

            List<UserAmigos> friendRequests = new List<UserAmigos>();
            List<int> idsUsuariosSolicitudes = new List<int>();

            foreach (SolicitudAmistad solicitud in usuarios.SolicitudesRecibidas)
            {

                idsUsuariosSolicitudes.Add(solicitud.UsuarioRecibeId);
                
                Usuario usuario = await _unitOfWork.UsuarioRepository.GetByIdAsync(solicitud.UsuarioEnviaId);

                friendRequests.Add(
                    new UserAmigos{
                    Id = usuario.Id,
                    NombreUsuario = usuario.NombreUsuario,
                    Foto = usuario.Foto,
                });
            }
            
            return friendRequests;


        }


        public async Task<string> DeleteFriendRequest(int id, int idFriend)
        {
            var user = await _unitOfWork.UsuarioRepository.GetUserByIdAndSolicitudes(id);
            var friend = await _unitOfWork.UsuarioRepository.GetUserByIdAndSolicitudes(idFriend);

            if (user == null)
                return "Usuario no encontrado";
            

            var request = user.SolicitudesRecibidas.FirstOrDefault(r => r.UsuarioEnviaId == idFriend);

            if (request == null)
                return "Solicitud de amistad no encontrada";
            

            user.SolicitudesRecibidas.Remove(request);
            friend.SolicitudesEnviadas.Remove(request);
            _unitOfWork.SolicitudAmistadRepository.Delete(request);

            await _unitOfWork.SaveAsync();

            return "Solicitud de amistad eliminada";
        }

        public async Task<List<UserAmigos>> GetUsersByUsernameForFriends(int id)
        {
            Usuario user = await _unitOfWork.UsuarioRepository.GetUserByIdAndFriends(id);
            ICollection<Usuario> usuarios = await _unitOfWork.UsuarioRepository.GetAllAsync();
            
            List<Usuario> amigosEnseniar = new List<Usuario>();
            bool amigo = false;
            foreach (Usuario usuario in usuarios)
            {
                if (usuario.Id != user.Id)
                {
                    amigo = false;

                    foreach (int ids in user.idAmigos)
                    {
                        if (usuario.Id == ids)
                            amigo = true;
                    }

                    if (!amigo)
                        amigosEnseniar.Add(usuario);
                }
                
            }


            return amigosEnseniar.Select(usuario => new UserAmigos
            {
                Id = usuario.Id,
                NombreUsuario = usuario.NombreUsuario,
                Foto = usuario.Foto,
            }).ToList();
        }

        public async Task<string> DeleteFriend(int id, int friendId)
        {
            Usuario user = await _unitOfWork.UsuarioRepository.GetUserByIdAndFriends(id);
            if (user == null)
                return "Usuario no encontrado";

            Usuario friend = await _unitOfWork.UsuarioRepository.GetUserByIdAndFriends(friendId);
            if (friend == null)
                return "Amigo no encontrado";


            if (user.idAmigos.Contains(friendId))
            {
                user.idAmigos.Remove(friendId);
                friend.idAmigos.Remove(id);

                _unitOfWork.UsuarioRepository.Update(user);
                _unitOfWork.UsuarioRepository.Update(friend);

                await _unitOfWork.SaveAsync();

                return "Amigo eliminado";
            }
            else
            {
                return "No es ampeigo";
            }
        }

        internal async Task<List<UserAmigos>> GetUsersByIdAndNameForFriends(int id, string name)
        {
            Usuario user = await _unitOfWork.UsuarioRepository.GetUserByIdAndFriends(id);
            ICollection<Usuario> usuarios = await _unitOfWork.UsuarioRepository.GetAllAsync();

            List<Usuario> amigosEnseniar = new List<Usuario>();
            bool amigo = false;
            foreach (Usuario usuario in usuarios)
            {
                if (usuario.Id != user.Id)
                {
                    amigo = false;

                    foreach (int ids in user.idAmigos)
                    {
                        if (usuario.Id == ids)
                            amigo = true;
                    }

                    if (!amigo)
                        amigosEnseniar.Add(usuario);
                }

            }

            List<Usuario> amigosfinal = new List<Usuario>();
            amigosfinal = amigosEnseniar.Where(usuario => usuario.NombreUsuario.Contains(name)).ToList();

            return amigosfinal.Select(usuario => new UserAmigos
            {
                Id = usuario.Id,
                NombreUsuario = usuario.NombreUsuario,
                Foto = usuario.Foto,
            }).ToList();
        }
    }
}
