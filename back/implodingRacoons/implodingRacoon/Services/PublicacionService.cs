using System.Collections;
using System.Collections.Generic;
using implodingRacoon.Models.Database;
using implodingRacoon.Models.Database.Dto;
using implodingRacoon.Models.Database.Entities;

namespace implodingRacoon.Services
{
    public class PublicacionService
    {
        private readonly UnitOfWork _unitOfWork;

        public PublicacionService(UnitOfWork unitOfWork)
        {
            _unitOfWork = unitOfWork;
        }

        public async Task<bool> CreatePost(publicarPublicacion publicacion)
        {

            var usuario = await _unitOfWork.UsuarioRepository.GetByIdAsync(publicacion.UsuarioId);

            var nuevaPublicacion = new Publicacion
            {
                Titulo = publicacion.Titulo,
                Descripcion = publicacion.Descripcion,
                Fecha = publicacion.Fecha,
                UsuarioId = publicacion.UsuarioId,
                Usuario = usuario,
                Comentarios = new List<Comentario>()
            };

            try { 
                _unitOfWork.PublicacionRepository.Add(nuevaPublicacion);
                await _unitOfWork.SaveAsync();
                return true;
            }
            catch (Exception ex)
            {
                return false;
            }

        }

        public async Task<List<PublicacionTarjetas>> GetPostsByUserId(int id)
        {
            var usuario = await _unitOfWork.UsuarioRepository.GetUserByIdAndPublicaciones(id);

            

            List<PublicacionTarjetas> listaPublicacionTarjetas = new List<PublicacionTarjetas>();

            foreach (Publicacion publicacionUsuario in usuario.Publicaciones)
            {
                var publicacion = await _unitOfWork.PublicacionRepository.GetPublicacionAndComents(publicacionUsuario.Id);
                
                listaPublicacionTarjetas.Add(new PublicacionTarjetas
                {
                    Id = publicacion.Id,
                    Titulo = publicacion.Titulo,
                    Comentarios = publicacion.Comentarios.Count,
                    Fecha = publicacion.Fecha,
                    nombreUsuario = usuario.NombreUsuario
                });
            }
            
            return listaPublicacionTarjetas;
        }

        public async Task<List<PublicacionTarjetas>> GetAllPosts()
        {
            var publicaciones = await _unitOfWork.PublicacionRepository.GetAllPublicacionesAndUserAsync();
            return publicaciones;
        }

        internal async Task<PublicacionInformacion> GetPostByUserId(int id)
        {
            var publicacion = await _unitOfWork.PublicacionRepository.GetPublicacionAndUser(id);
            return publicacion;
        }

        internal async Task<List<PublicacionTarjetas>> GetPostByName(string name)
        {
            var publicaciones = await _unitOfWork.PublicacionRepository.GetAllPublicacionesAndUserAsync();

            List<PublicacionTarjetas> listaPublicacionInformacion = new List<PublicacionTarjetas>();

            foreach (PublicacionTarjetas publicacion in publicaciones)
            {
                if (publicacion.nombreUsuario.Contains(name))
                {
                    listaPublicacionInformacion.Add(publicacion);
                }
            }

            return listaPublicacionInformacion;
        }

        internal async Task<ICollection<ComentarioSimple>> GetComentsByPostId(int id)
        {
            PublicacionComentarios publicaciones = await _unitOfWork.PublicacionRepository.GetPublicacionAndComents(id);

            ICollection<ComentarioSimple> listComentarios = publicaciones.Comentarios;

            return listComentarios;
        }

        internal async Task<bool> CreateComment(publicarComentario comentario)
        {

            var publicacion = await _unitOfWork.PublicacionRepository.GetPublicacionAndComents(comentario.PublicacionId);
            var usuario = await _unitOfWork.UsuarioRepository.GetByIdAsync(comentario.UsuarioId);

            if (publicacion == null || usuario == null)
                return false; 
           
            var nuevoComentario = new Comentario
            {
                Descripcion = comentario.Comentario,
                Fecha = comentario.Fecha,
                PublicacionId = comentario.PublicacionId,
                UsuarioId = comentario.UsuarioId,
            };

            try
            {
                _unitOfWork.ComentarioRepository.Add(nuevoComentario);
                await _unitOfWork.SaveAsync();
                return true;
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex);
                return false;
            }
        }
    }
}
