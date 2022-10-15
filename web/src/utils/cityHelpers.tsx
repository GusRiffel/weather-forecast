export const hasSearchedCity = (cities: string[]) => (city: string) => {
  return cities.some((c) => c.toUpperCase() === city.toUpperCase());
};
