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
    }
}
