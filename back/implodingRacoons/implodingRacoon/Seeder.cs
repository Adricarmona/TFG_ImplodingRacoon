using implodingRacoon.Models.Database;
using implodingRacoon.Models.Database.Entities;

namespace implodingRacoon
{
    public class Seeder
    {
        private readonly ImplodingRacoonsContext _implodingRacoonsContext;
        public Seeder(ImplodingRacoonsContext implodingRacoonsContext)
        {
            _implodingRacoonsContext = implodingRacoonsContext;
        }

        public async Task SeedAsync()
        {
            await SeedDisenioAsync();

            await SeedCardsAsync();

            await _implodingRacoonsContext.SaveChangesAsync();
        }

        private async Task SeedCardsAsync()
        {
            Carta[] cards =
            [
                new Carta(){
                    Titulo = "C4",
                    Descripcion = "La carta que te explota",
                    Tipo = "C4",
                    Disenos = _implodingRacoonsContext.Diseno.Where(d => d.Nombre == "C4_Original").ToList()
                },
                new Carta(){
                    Titulo = "Defuser",
                    Descripcion = "Carta que te desactiva la el C4",
                    Tipo = "Defuser",
                    Disenos = _implodingRacoonsContext.Diseno.Where(d => d.Nombre == "Defuser_Original").ToList()
                },
                new Carta(){
                    Titulo = "Favor",
                    Descripcion = "El jugador que elijas tiene que darte una carta de su eleccion",
                    Tipo = "Favor",
                    Disenos = _implodingRacoonsContext.Diseno.Where(d => d.Nombre == "Favor_Original").ToList()
                },
                new Carta(){
                    Titulo = "GatoBarba",
                    Descripcion = "Si consigues 3 de estas cartas puedes robar carta aleatoria del jugador que elijas",
                    Tipo = "GatoBarba",
                    Disenos = _implodingRacoonsContext.Diseno.Where(d => d.Nombre == "GatoBarba_Original").ToList()
                },
                new Carta(){
                    Titulo = "GatoMelon",
                    Descripcion = "Si consigues 3 de estas cartas puedes robar carta aleatoria del jugador que elijas",
                    Tipo = "GatoMelon",
                    Disenos = _implodingRacoonsContext.Diseno.Where(d => d.Nombre == "GatoMelon_Original").ToList()
                },
                new Carta(){
                    Titulo = "GatoPeludo",
                    Descripcion = "Si consigues 3 de estas cartas puedes robar carta aleatoria del jugador que elijas",
                    Tipo = "GatoPeludo",
                    Disenos = _implodingRacoonsContext.Diseno.Where(d => d.Nombre == "GatoPeludo_Original").ToList()
                },
                new Carta(){
                    Titulo = "Nop",
                    Descripcion = "La carta que te dice que no a la accion de otro jugador",
                    Tipo = "Nop",
                    Disenos = _implodingRacoonsContext.Diseno.Where(d => d.Nombre == "Nop_Original").ToList()
                },
                new Carta(){
                    Titulo = "Salta",
                    Descripcion = "Carta que hace que saltes tu turno sin robar carta",
                    Tipo = "Salta",
                    Disenos = _implodingRacoonsContext.Diseno.Where(d => d.Nombre == "Salta_Original").ToList()
                },
                new Carta(){
                    Titulo = "TacoCat",
                    Descripcion = "Si consigues 3 de estas cartas puedes robar carta aleatoria del jugador que elijas",
                    Tipo = "TacoCat",
                    Disenos = _implodingRacoonsContext.Diseno.Where(d => d.Nombre == "TacoCat_Original").ToList()
                },
                new Carta(){
                    Titulo = "VerFuturo",
                    Descripcion = "La carta que te deja ver las 3 siguientes cartas del mazo",
                    Tipo = "VerFuturo",
                    Disenos = _implodingRacoonsContext.Diseno.Where(d => d.Nombre == "VerFuturo_Original").ToList()
                },
                new Carta(){
                    Titulo = "VomitoArcoiris",
                    Descripcion = "Si consigues 3 de estas cartas puedes robar carta aleatoria del jugador que elijas",
                    Tipo = "VomitoArcoiris",
                    Disenos = _implodingRacoonsContext.Diseno.Where(d => d.Nombre == "VomitoArcoiris_Original").ToList()
                },
                new Carta(){
                    Titulo = "Ataca",
                    Descripcion = "La carta que hace que no robes carta y el siguiente jugador roba 2",
                    Tipo = "Ataca",
                    Disenos = _implodingRacoonsContext.Diseno.Where(d => d.Nombre == "Ataca_Original").ToList()
                },
                new Carta(){
                    Titulo = "Baraja",
                    Descripcion = "Baraja el mazo central",
                    Tipo = "Baraja",
                    Disenos = _implodingRacoonsContext.Diseno.Where(d => d.Nombre == "Baraja_Original").ToList()
                },
                new Carta(){
                    Titulo = "ParteAtras",
                    Descripcion = "La parte de atras de la baraja",
                    Tipo = "ParteAtras",
                    Disenos = _implodingRacoonsContext.Diseno.Where(d => d.Nombre == "ParteAtras_Original").ToList()
                }
            ];

            await _implodingRacoonsContext.Carta.AddRangeAsync(cards);
        }

        private async Task SeedDisenioAsync()
        {
            Diseno[] disenio =
            [
                new Diseno()
                {
                    Nombre = "Ataca_Original",
                    Imagen = "https://localhost:7089/cards/ataca.png"
                },
                new Diseno()
                {
                    Nombre = "Baraja_Original",
                    Imagen = "https://localhost:7089/cards/baraja.png"
                },
                new Diseno()
                {
                    Nombre = "C4_Original",
                    Imagen = "https://localhost:7089/cards/c4.png"
                },
                new Diseno()
                {
                    Nombre = "Defuser_Original",
                    Imagen = "https://localhost:7089/cards/defuser.png"
                },
                new Diseno()
                {
                    Nombre = "Favor_Original",
                    Imagen = "https://localhost:7089/cards/favor.png"
                },
                new Diseno()
                {
                    Nombre = "GatoBarba_Original",
                    Imagen = "https://localhost:7089/cards/gatoBarba.png"
                },
                new Diseno()
                {
                    Nombre = "GatoMelon_Original",
                    Imagen = "https://localhost:7089/cards/gatoMelon.png"
                },
                new Diseno()
                {
                    Nombre = "GatoPeludo_Original",
                    Imagen = "https://localhost:7089/cards/gatoPeludo.png"
                },
                new Diseno()
                {
                    Nombre = "Nop_Original",
                    Imagen = "https://localhost:7089/cards/nop.png"
                },
                new Diseno()
                {
                    Nombre = "Salta_Original",
                    Imagen = "https://localhost:7089/cards/salta.png"
                },
                new Diseno()
                {
                    Nombre = "Nop_Original",
                    Imagen = "https://localhost:7089/cards/nop.png"
                },
                new Diseno()
                {
                    Nombre = "TacoCat_Original",
                    Imagen = "https://localhost:7089/cards/tacoCat.png"
                },
                new Diseno()
                {
                    Nombre = "VerFuturo_Original",
                    Imagen = "https://localhost:7089/cards/verFuturo.png"
                },
                new Diseno()
                {
                    Nombre = "VomitoArcoiris_Original",
                    Imagen = "https://localhost:7089/cards/vomitoArcoiris.png"
                },
                new Diseno()
                {
                    Nombre = "ParteAtras_Original",
                    Imagen = "https://localhost:7089/cards/parteAtras.png"
                }
            ];

            await _implodingRacoonsContext.Diseno.AddRangeAsync(disenio);
        }
    }
}
