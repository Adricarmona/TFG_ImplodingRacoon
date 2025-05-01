using implodingRacoon.Models.Database;
using implodingRacoon.Models.Database.Dto;
using implodingRacoon.Models.Database.Entities;

namespace implodingRacoon.Services
{
    public class CardsService
    {
        private readonly UnitOfWork _unitOfWork;

        public CardsService(UnitOfWork unitOfWork)
        {
            _unitOfWork = unitOfWork;
        }

        public async Task<ICollection<Carta>> GetAllCardsAsync()
        {
            var cards = await _unitOfWork.CartaRepository.GetAllAsync();
            return cards;
        }

        public async Task<Carta> GetCardByIdAsync(int id)
        {
            var card = await _unitOfWork.CartaRepository.GetByIdAsync(id);
            return card;
        }

        public async Task<ICollection<Carta>> GetAllCardsAndDisenioAsync()
        {
            var cards = await _unitOfWork.CartaRepository.GetCartaWhithDisenio();
            return cards;
        }

        public async Task<Carta> GetCardByIdAndDisenioAsync(int id)
        {
            var cards = await _unitOfWork.CartaRepository.GetCartaWhithDisenioById(id);
            return cards;
        }

        public async Task<CardWithDisenio> GetCardByIdAndDisenioTypeImageAsync(int id, TypeCards type)
        {
            var cards = await _unitOfWork.CartaRepository.GetCartaWhithDisenioById(id);

            String urlimagen = "other";
            if (type == TypeCards.Original)
            {
                foreach (Diseno diseno in cards.Disenos)
                {
                    if (diseno.Nombre.Contains("Original"))
                    {
                        urlimagen = diseno.Imagen;
                        break;
                    }
                }
            } 
            else
            {
                urlimagen = "https://images.steamusercontent.com/ugc/1242379645142561979/40C6DB79932467F7B2E540CD75FC6033E5BF8B57/?imw=637&imh=358&ima=fit&impolicy=Letterbox&imcolor=%23000000&letterbox=true"; // aqui no deveria entrar nunca por ahora
            }

            CardWithDisenio cardWithDisenio = new CardWithDisenio
                {
                    Id = cards.Id,
                    Titulo = cards.Titulo,
                    Descripcion = cards.Descripcion,
                    Tipo = cards.Tipo,
                    urlImage = urlimagen
                };

            return cardWithDisenio;
        }

        public async Task<List<CardWithDisenio>> GetAllCardAndDisenioTypeImageAsync(TypeCards type)
        {
            var cards = await _unitOfWork.CartaRepository.GetCartaWhithDisenio();

            String urlimagen = "other";
            List<CardWithDisenio> listCards = new();

            foreach (Carta card in cards)
            {
                if (type == TypeCards.Original)
                {
                    foreach (Diseno diseno in card.Disenos)
                    {
                        if (diseno.Nombre.Contains("Original"))
                        {
                            urlimagen = diseno.Imagen;
                            break;
                        }
                    }
                }
                else
                {
                    urlimagen = "https://images.steamusercontent.com/ugc/1242379645142561979/40C6DB79932467F7B2E540CD75FC6033E5BF8B57/?imw=637&imh=358&ima=fit&impolicy=Letterbox&imcolor=%23000000&letterbox=true"; // aqui no deveria entrar nunca por ahora
                }

                CardWithDisenio cardWithDisenio = new CardWithDisenio
                {
                    Id = card.Id,
                    Titulo = card.Titulo,
                    Descripcion = card.Descripcion,
                    Tipo = card.Tipo,
                    urlImage = urlimagen
                };

                listCards.Add(cardWithDisenio);

            }





            return listCards;
        }
    }
}
