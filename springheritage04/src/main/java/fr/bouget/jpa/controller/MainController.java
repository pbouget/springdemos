package fr.bouget.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.bouget.jpa.model.Barrique;
import fr.bouget.jpa.model.Bouteille;
import fr.bouget.jpa.model.Vin;
import fr.bouget.jpa.service.ArticleService;

/**
 * @author Philippe
 *
 */
@RestController
@CrossOrigin("*")
public class MainController{


	@Autowired
	private ArticleService service;
	
	@GetMapping("/")
	@ResponseBody
	public String home()
	{
		init();
		StringBuilder sb = new StringBuilder();
		sb.append("<h1>Regardez dans votre base de donn�es MySQL <strong>JPA</strong></h1>");
		sb.append("<p>Vous devez avoir <strong>3 tables</strong> :</p>");
		sb.append("<li><a href='http://localhost:8080/barriques'>Liste des <strong>Barriques</strong> enregistr�es</a></li>");
		sb.append("<li><a href='http://localhost:8080/bouteilles'>Liste des <strong>Bouteilles</strong> enregistr�es</a></li>");
		sb.append("<li><a href='http://localhost:8080/vins'>Liste des <strong>Vins</strong> enregistr�s</a></li></ul>");

		return  sb.toString();
	}

	@GetMapping(value = "/barriques")
	public ResponseEntity<?> getAllBarriques(){
		
		return service.getAllBarriques();
	}

	@GetMapping(value = "/bouteilles")
	public ResponseEntity<?> getAllBouteilles(){
		 
		return service.getAllBouteilles();
	}

	
	@GetMapping(value = "/vins")
	public ResponseEntity<?> getAllVins(){
		return service.getAllVins();
	}

	/**
	 * M�thode d'initialisation (d�mo)
	 * On met plut�t un fichier data.sql dans le dossier resources
	 * non utilis�e
	 */
	private void init()
	{
       
        Barrique bar1=new Barrique();
        bar1.setCodeArticle(629043);
        bar1.setDesignation("Barrique en ch�ne");
        bar1.setRegion("Bordeaux (Margaux)");
        bar1.setCouleur("rouge");
        bar1.setPrix(85);
        bar1.setRemise(0);
        bar1.setQuantite(8);
        bar1.setContenance(1000);
        bar1.setAnnee(2015);
        
        Bouteille b1=new Bouteille();
        b1.setCodeArticle(765439);
        b1.setDesignation("Les Hauts du Tertre 1999");
        b1.setRegion("Bordeaux (Margaux)");
        b1.setCouleur("rouge");
        b1.setPrix(11.50);
        b1.setRemise(0);
        b1.setQuantite(2);
        b1.setContenance(75);
        b1.setAnnee(2019);
        
        Bouteille b2=new Bouteille();
        b2.setCodeArticle(543289);
        b2.setDesignation("Ch�teau Marquis de Terme 1998");
        b2.setRegion("Bordeaux (Margaux)");
        b2.setCouleur("rouge");
        b2.setPrix(19.00);
        b2.setRemise(0);
        b2.setQuantite(3);
        b2.setContenance(75);
        b2.setAnnee(2018);
        
        Bouteille b3=new Bouteille();
        b3.setCodeArticle(278237);
        b3.setDesignation("Clos du Marquis 1999");
        b3.setRegion("Bordeaux (Saint-Julien)");
        b3.setCouleur("rouge");
        b3.setPrix(22.90);
        b3.setRemise(0);
        b3.setQuantite(15);
        b3.setContenance(75);
        b3.setAnnee(2000);
        
        Bouteille b4=new Bouteille();
        b4.setCodeArticle(974534);
        b4.setDesignation("Clos du Baron 1998");
        b4.setRegion("Bordeaux (Saint-Julien)");
        b4.setCouleur("blanc");
        b4.setPrix(45.20);
        b4.setRemise(0);
        b4.setQuantite(54);
        b4.setContenance(75);
        b4.setAnnee(2018);
        
        Vin v1 = new Vin();
        v1.setCodeArticle(666666);
        v1.setDesignation("Pinard de Simplon");
        v1.setRegion("Montreuil");
        v1.setCouleur("ros�");
        v1.setPrix(3.80);
        v1.setRemise(0);
        v1.setQuantite(150);
        v1.setAnnee(2016);
        		
        Vin v2 = new Vin();
        v2.setCodeArticle(777777);
        v2.setDesignation("Bi�re de Simplon");
        v2.setRegion("Montreuil � Perpette les oies");
        v2.setCouleur("Jaune");
        v2.setPrix(8.99);
        v2.setRemise(0);
        v2.setQuantite(200);
        v2.setAnnee(2015);
	        
       
        System.out.println("ajout du produit: "+bar1);
        service.save(bar1);
        
        System.out.println("ajout du produit: "+b1);
        service.save(b1);
        
        System.out.println("ajout du produit: "+b2);
        service.save(b2);
        
        System.out.println("ajout du produit: "+b3);
        service.save(b3);
        
        System.out.println("ajout du produit: "+b4);
        service.save(b4);
        
        System.out.println("ajout du produit: "+v1);
        service.save(v1);
        
        System.out.println("ajout du produit: "+v2);
        service.save(v2);

       
        System.out.println("modification du produit "+b3.getDesignation()+ "(10 bouteilles)");
        b3.setQuantite(10);
        service.save(b3);
           
      
        System.out.println("modification du produit "+b4.getDesignation()+ "(50 bouteilles)");
        b4.setQuantite(50);
        service.save(b4);
        
       
        System.out.println("suppression de l'article "+b2.getDesignation());
        service.save(b2); 

	}

}



