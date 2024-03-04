package assessment.parkinglot.enums;

import assessment.parkinglot.entities.ParkingSpaceEntity;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public enum VehicleType {

    BIKE(Arrays.asList(SpaceType.BIKE), 1, (spaces) -> Arrays.asList(spaces.get(0))),
    // we try to find a compact space or else we return a regular one
    CAR(Arrays.asList(SpaceType.COMPACT, SpaceType.REGULAR), 1, (spaces) ->
            Arrays.asList(spaces.stream()
                    .filter(space -> SpaceType.COMPACT.equals(space.getType()))
                    .findFirst()
                    .orElseGet(() -> spaces.get(0)))),
    // 3 regular space - no beside validation
    VAN(Arrays.asList(SpaceType.REGULAR), 3, (spaces) -> spaces.subList(0, 3));

    private List<SpaceType> spaceTypes;
    private int spacesRequired;
    private Function<List<ParkingSpaceEntity>, List<ParkingSpaceEntity>> getSpaces;

    VehicleType(List<SpaceType> types, int spacesRequired, Function<List<ParkingSpaceEntity>, List<ParkingSpaceEntity>> getSpaces) {
        this.spaceTypes = types;
        this.spacesRequired = spacesRequired;
        this.getSpaces = getSpaces;
    }

    public List<SpaceType> getSpaceTypes() {
        return this.spaceTypes;
    }

    public int getSpacesRequired() {
        return this.spacesRequired;
    }

    public List<ParkingSpaceEntity> getSpaces(List<ParkingSpaceEntity> spaces) {
        return this.getSpaces.apply(spaces);
    }

}
