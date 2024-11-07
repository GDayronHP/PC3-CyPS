package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.domain.VetTO;
import com.tecsup.petclinic.entities.Vet;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VetMapper {

    VetMapper INSTANCE = Mappers.getMapper(VetMapper.class);

    VetTO toVetTO(Vet vet);

    Vet toVet(VetTO vetTO);

    List<VetTO> toVetTOList(List<Vet> vets);

    List<Vet> toVetList(List<VetTO> vetTOs);
}