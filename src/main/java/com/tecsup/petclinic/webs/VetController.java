package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.domain.VetTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exception.VetNotFoundException;
import com.tecsup.petclinic.mapper.VetMapper;
import com.tecsup.petclinic.services.VetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class VetController {

    //@Autowired
    private VetService vetService;

    //@Autowired
    private VetMapper mapper;

    /**
     *  Change
     * @param vetService
     * @param mapper
     */
    public VetController(VetService vetService, VetMapper mapper){
        this.vetService = vetService;
        this.mapper = mapper ;
    }

    /**
     * Get all vets
     *
     * @return
     */
    @GetMapping(value = "/vets")
    public ResponseEntity<List<VetTO>> findAllVets() {

        List<Vet> vets = vetService.findAll();
        log.info("vets: " + vets);
        vets.forEach(item -> log.info("Vet >>  {} ", item));

        List<VetTO> vetsTO = this.mapper.toVetTOList(vets);
        log.info("vetsTO: " + vetsTO);
        vetsTO.forEach(item -> log.info("VetTO >>  {} ", item));

        return ResponseEntity.ok(vetsTO);
    }

    /**
     * Create vet
     *
     * @param vetTO
     * @return
     */
    @PostMapping(value = "/vets")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<VetTO> create(@RequestBody VetTO vetTO) {

        Vet newVet = this.mapper.toVet(vetTO);
        VetTO newVetTO = this.mapper.toVetTO(vetService.create(newVet));

        return  ResponseEntity.status(HttpStatus.CREATED).body(newVetTO);

    }


    /**
     * Find vet by id
     *
     * @param id
     * @return
     * @throws VetNotFoundException
     */
    @GetMapping(value = "/vets/{id}")
    ResponseEntity<VetTO> findById(@PathVariable Integer id) {

        VetTO vetTO = null;

        try {
            Vet vet = vetService.findById(id);
            vetTO = this.mapper.toVetTO(vet);

        } catch (VetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(vetTO);
    }

    /**
     * Update and create vet
     *
     * @param vetTO
     * @param id
     * @return
     */
    @PutMapping(value = "/vets/{id}")
    ResponseEntity<VetTO>  update(@RequestBody VetTO vetTO, @PathVariable Integer id) {

        VetTO updateVetTO = null;

        try {

            Vet updateVet = vetService.findById(id);

            updateVet.setFirstName(vetTO.getFirstName());
            updateVet.setLastName(vetTO.getLastName());

            vetService.update(updateVet);

            updateVetTO = this.mapper.toVetTO(updateVet);

        } catch (VetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updateVetTO);
    }

    /**
     * Delete vet by id
     *
     * @param id
     */
    @DeleteMapping(value = "/vets/{id}")
    ResponseEntity<String> delete(@PathVariable Integer id) {

        try {
            vetService.delete(id);
            return ResponseEntity.ok(" Delete ID :" + id);
        } catch (VetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}