package it.polito.tdp.itunes.model;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	
	private ItunesDAO dao;
	private Graph<Album, DefaultEdge> grafo;
	private List<Album> albums;
	private Map<Integer, Album> albumIdMap;
	private List<Coppia> archi;
	private int totBrani;	
	
	//per la ricorsione
	private Set<Album> componenteConnessa;
	private int dimMax;
	private List<Album> setMax; //risultato che devo restituire
	
	
	public void creaGrafo(double d) {
		double dmill = d*1000;
		this.dao = new ItunesDAO();
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		
		this.albums = dao.getSelectedAlbums(dmill);
		Graphs.addAllVertices(this.grafo, this.albums);
		
		this.albumIdMap = new HashMap<>();
		for (Album a : albums)
			albumIdMap.put(a.getAlbumId(), a);
		
		this.archi = dao.getArchi(albumIdMap);
		for (Coppia c : archi) {
			this.grafo.addEdge(c.getA1(), c.getA2());
		}
	}
	
	public int getNumV() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNumE() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Album> getVertici(){
		return this.albums;
	}
	
	public int dimConnessa(Album a) {
		int dimConnessa = 0;
		this.totBrani = 0;
		ConnectivityInspector<Album, DefaultEdge> ci = new ConnectivityInspector<>(this.grafo);
		for (Set<Album> set: ci.connectedSets()) {
			if (set.contains(a)) {
				dimConnessa = set.size();
				this.componenteConnessa = set;
				for (Album aa : set) {
					this.totBrani += dao.contaBrani(aa);
				}
			}
		}
		return dimConnessa;
	}
	
	public int getTotaleBrani() {
		return this.totBrani;
	}
	
	//interfaccia pubblica della ricorsione
	public Set<Album> ricercaSetMassimo(Album a1, double dTot){
		
		if (a1.getDurata() > dTot)
			return null;
		
		List<Album> parziale = new ArrayList<>();
		parziale.add(a1); //elemento che deve stare per forza dentro parziale
		List<Album> tutti = new ArrayList<>(this.componenteConnessa);
		tutti.remove(a1);
		
		dimMax = 1;
		setMax = new ArrayList<>(parziale);
		//se non trovassi niente di meglio il mio set max è una copia di parziale 
		//(che in questo momento contiene solo i vertice di partenza a1)
		
		ricorsione(parziale, 1, a1.getDurata(), dTot, tutti);
			
		return new HashSet<>(setMax);
	}
	
	private void ricorsione(List<Album> parziale, int livello, double durataParziale,
			double dTot, List<Album> tutti) {
		
		//condizione di terminazione
		if (parziale.size() > dimMax) {
			//ho trovato una soluzione migliore
			dimMax = parziale.size();
			setMax = new ArrayList<>(parziale);
		}
		
		//ciclo su tutti gli album
		for (Album nuovo : tutti) {
			if (!parziale.contains(nuovo) && nuovo.getDurata()+durataParziale <= dTot) {
				parziale.add(nuovo);
				ricorsione(parziale, livello+1, durataParziale+nuovo.getDurata(), dTot, tutti);
				//backtracking
				parziale.remove(nuovo);
			}
		}
	}
	
	
}
