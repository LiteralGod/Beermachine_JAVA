package Presentation;

import Domain.BeerType;

import java.util.List;

public interface IDomainHandler {

   void StartMachine(float beerType, float beerSpeed);

   void StopMachine();

   void ResetMachine();

   List<BeerType> ListOfBeerTypes();
}
