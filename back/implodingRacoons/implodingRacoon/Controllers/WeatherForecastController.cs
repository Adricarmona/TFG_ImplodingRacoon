using Microsoft.AspNetCore.Mvc;

namespace juegoMemoria.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class WeatherForecastController : ControllerBase
    {
        [HttpGet(Name = "GetWeatherForecast")]
        public string Get()
        {
            return "yes";
        }
    }
}
