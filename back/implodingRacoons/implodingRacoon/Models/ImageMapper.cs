using implodingRacoon.Models.Database.Dto;
using implodingRacoon.Services.Extensions;

namespace implodingRacoon.Models
{
    public class ImageMapper
    {

        public CardWithDisenio AddCorrectPathCards(CardWithDisenio cardWithDisenio, HttpRequest httpRequest = null)
        {
            return new CardWithDisenio()
            {
                Id = cardWithDisenio.Id,
                Titulo = cardWithDisenio.Titulo,
                Descripcion = cardWithDisenio.Descripcion,
                Tipo = cardWithDisenio.Tipo,
                urlImage = httpRequest is null ? cardWithDisenio.urlImage : httpRequest.GetAbsoluteUrl(cardWithDisenio.urlImage)
            };
        }

        public IEnumerable<CardWithDisenio> AddCorrectPathCards(IEnumerable<CardWithDisenio> images, HttpRequest httpRequest = null)
        {
            return images.Select(image => AddCorrectPathCards(image, httpRequest));
        }

        public UserAmigos AddCorrectPathUserAmigo(UserAmigos userAmigos, HttpRequest httpRequest = null)
        {
            return new UserAmigos()
            {
                Id = userAmigos.Id,
                NombreUsuario = userAmigos.NombreUsuario,
                Foto = httpRequest is null ? userAmigos.Foto : httpRequest.GetAbsoluteUrl(userAmigos.Foto)
            };
        }

        public IEnumerable<UserAmigos> AddCorrectPathUserAmigo(IEnumerable<UserAmigos> images, HttpRequest httpRequest = null)
        {
            return images.Select(image => AddCorrectPathUserAmigo(image, httpRequest));
        }

        public string AddCorrectPathImage(string img, HttpRequest httpRequest = null)
        {
            return httpRequest is null ? img : httpRequest.GetAbsoluteUrl(img);
        }

        public IEnumerable<string> AddCorrectPathImage(IEnumerable<string> images, HttpRequest httpRequest = null)
        {
            return images.Select(image => AddCorrectPathImage(image, httpRequest));
        }
    }
}
