using implodingRacoon.Models.Database.Entities;
using implodingRacoon.Models.Database.Repository.Repository;
using Microsoft.EntityFrameworkCore;

namespace implodingRacoon.Models.Database.Repository
{
    public class CartaRepository : Repository<Carta, int>
    {
        public CartaRepository(ImplodingRacoonsContext context) : base(context) { }


        public async Task<List<Carta>> GetCartaWhithDisenio()
        {
            return await GetQueryable()
                .Include(carta => carta.Disenos)
                .ToListAsync();
        }

        public async Task<Carta> GetCartaWhithDisenioById(int id)
        {
            return await GetQueryable()
                .Include(carta => carta.Disenos)
                .FirstOrDefaultAsync(carta => carta.Id == id);
        }
    }
}
