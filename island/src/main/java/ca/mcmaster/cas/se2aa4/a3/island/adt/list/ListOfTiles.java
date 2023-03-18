package ca.mcmaster.cas.se2aa4.a3.island.adt.list;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.adt.profile.*;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.BaseType;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Tile;

import java.util.ArrayList;
import java.util.List;

public class ListOfTiles implements Colorable{
    List<Tile> tiles = new ArrayList<>();

    public void addTile(Structs.Polygon foundation, BaseType baseType){
        tiles.add(new Tile(foundation, baseType));
    }

    public Tile getTile(Structs.Polygon foundation){
        for (Tile t : tiles){
            if (t.getFoundationPolygon().equals(foundation)){
                return t;
            }
        }
        return null;
    }

    public void setTile(Structs.Polygon foundation, BaseType baseType) {
        this.getTile(foundation).setBaseType(baseType);
    }

    /*
    public void calculateAltitude(AltimetricProfile profile, int seed){
        //For each tile in tiles, do calculation and call setAltitude
    }

    public void calculateAbsorption(SoilProfile profile, int seed){
        //For each tile in tiles, do calculation and call setAbsorption
    }

     */

    public void calculateBiome(WhittakerProfile profile){
        for (Tile t : tiles){
            t.calculateBiome(profile);
        }
    }

    @Override
    public Structs.Mesh addColor(Structs.Mesh inputMesh) {
        List<Structs.Polygon> newPolygons = new ArrayList<>();
        for (Tile t : tiles){
            Structs.Polygon p = t.getFoundationPolygon();
            newPolygons.add(Structs.Polygon.newBuilder(p).addProperties(t.getDefaultColor()).build());
        }
        return Structs.Mesh.newBuilder(inputMesh).addAllPolygons(newPolygons).build();
    }
}