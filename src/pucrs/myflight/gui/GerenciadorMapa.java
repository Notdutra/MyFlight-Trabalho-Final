package pucrs.myflight.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jxmapviewer.JXMapKit;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;
import org.jxmapviewer.viewer.WaypointRenderer;

import net.sf.geographiclib.*;

import pucrs.myflight.modelo.Geo;

/**
 * Classe para gerenciar um mapa
 *
 * @author Marcelo Cohen
 */
public class GerenciadorMapa {

	final JXMapKit jXMapKit;
	private WaypointPainter<MyWaypoint> pontosPainter;

	private GeoPosition selCentro;

	private ArrayList<Tracado> linhas;

	private int maxZoomText;

	private double wxmin, wxmax;
	private double deltaldp;

	private Font font;
	
	private boolean useGeodesic; // true = desenha linhas geodésicas de um ponto a outro, false = desenha linhas simples

	public enum FonteImagens {

		OpenStreetMap, VirtualEarth
	};

	/*
	 * Cria um gerenciador de mapas, a partir de uma posição e uma fonte de
	 * imagens
	 * 
	 * @param centro centro do mapa
	 * 
	 * @param fonte fonte das imagens (FonteImagens.OpenStreetMap ou
	 * FonteImagens.VirtualEarth)
	 */
	public GerenciadorMapa(GeoPosition centro, FonteImagens fonte) {
		jXMapKit = new JXMapKit();
		TileFactoryInfo info = null;
		if (fonte == FonteImagens.OpenStreetMap) {
			info = new OSMTileFactoryInfo();
		} else {
			info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
		}
		DefaultTileFactory tileFactory = new DefaultTileFactory(info);
		jXMapKit.setTileFactory(tileFactory);

		maxZoomText = 10;

		// Ajustando a opacidade do mapa (50%)
		jXMapKit.getMainMap().setAlpha(0.5f);

		// Ajustando o nível de zoom do mapa
		jXMapKit.setZoom(10);
		// Informando o centro do mapa
		jXMapKit.setAddressLocation(centro);
		// Indicando que não desejamos ver um marcador nessa posição
		jXMapKit.setAddressLocationShown(false);

		wxmin = jXMapKit.getMainMap().getTileFactory().getInfo().getLongitudeDegreeWidthInPixels(17);
		wxmax = jXMapKit.getMainMap().getTileFactory().getInfo().getLongitudeDegreeWidthInPixels(1);
		// System.out.println("Min. lon. degree in pixels: "+wxmin);
		// System.out.println("Max. lon. degree in pixels: "+wxmax);
		deltaldp = wxmax - wxmin;

		font = new Font("Sans", Font.PLAIN, 20);
		
		useGeodesic = true;

		// Criando um objeto para "pintar" os pontos
		pontosPainter = new WaypointPainter<MyWaypoint>();

		// Criando um objeto para desenhar os pontos
		pontosPainter.setRenderer(new WaypointRenderer<MyWaypoint>() {

			@Override
			public void paintWaypoint(Graphics2D g, JXMapViewer viewer, MyWaypoint wp) {

				// Desenha cada waypoint como um pequeno círculo
				Point2D point = viewer.getTileFactory().geoToPixel(wp.getPosition(), viewer.getZoom());
				float x = (float) point.getX();
				float y = (float) point.getY();

				// Seta a cor do waypoint
				g.setColor(wp.getColor());
				double s = wp.getSize();
				double zoom = jXMapKit.getZoomSlider().getValue();

				double zoomfat = viewer.getTileFactory().getInfo().getLongitudeDegreeWidthInPixels((int) zoom);
				double fat = (zoomfat - wxmin) / deltaldp * 2;
				// System.out.println("Fat: "+fat+" zoom: "+zoom);
				// s = (1+s)+100*fat;
				s = 1 + s * 1000 * fat;
				// System.out.println("zoom:"+zoom+" - "+s);
				int offset = (int) s;
				g.fill(new Ellipse2D.Double(x - s, y - s, s + s, s + s));
				g.setFont(font);
				if (jXMapKit.getZoomSlider().getValue() < maxZoomText) {
					// if(wp.getLabel().equals("POA"))
					// System.out.println("POA: "+x+" "+y);
					g.setColor(Color.BLACK);
					g.drawString(wp.getLabel(), x + offset + 1, y + offset + 1);
					g.setColor(wp.getColor());
					g.drawString(wp.getLabel(), x + offset, y + offset);
				}

			}
		});

		// Criando um objeto para desenhar o traçado das linhas
		Painter<JXMapViewer> linhasPainter = new Painter<JXMapViewer>() {

			@Override
			public void paint(Graphics2D g, JXMapViewer map, int w, int h) {
				
				for (Tracado tr : linhas) {
					ArrayList<Geo> pontos = tr.getPontos();
					Color cor = tr.getCor();
					int x[] = new int[pontos.size()];
					int y[] = new int[pontos.size()];
					
					Point2D p0 = map.convertGeoPositionToPoint(pontos.get(0));
					Point2D p1 = map.convertGeoPositionToPoint(pontos.get(1));
					
					int xmid = (int)(p0.getX() + p1.getX()) / 2;
					int ymid = (int)(p0.getY() + p1.getY()) / 2;
					g.setColor(Color.RED);

					// Desenha label no primeiro trecho da rota
					// Muito lento e polui demais a tela se houver muitas rotas
					
//					int dx = (int) (p0.getX() - p1.getX());
//					int dy = (int) (p0.getY() - p1.getY());
//					double theta = Math.atan2(dx, dy);
//					g.translate(xmid, ymid);
//				    g.rotate(theta);
//				    g.drawString(tr.getLabel(), xmid, ymid);
//				    g.rotate(-theta);
//				    g.translate(-xmid,-ymid);									
					
					for (int i = 0; i < pontos.size(); i++) {
						Point2D point = map.convertGeoPositionToPoint(pontos.get(i));
						x[i] = (int) point.getX();
						y[i] = (int) point.getY();
					}
					// int xPoints[] = { 0, 20, 40, 100, 120 };
					// int yPoints[] = { 0, 20, 40, 100, 120 };
					g.setColor(cor);
					g.setStroke(new BasicStroke(tr.getWidth()));
					g.drawPolyline(x, y, x.length);
				}
			}

		};

		// Criando um objeto para desenhar os elementos de interface
		// (ponto selecionado, etc)
		Painter<JXMapViewer> guiPainter = new Painter<JXMapViewer>() {
			public void paint(Graphics2D g, JXMapViewer map, int w, int h) {
				if (selCentro == null)
					return;
				Point2D point = map.convertGeoPositionToPoint(selCentro);
				int x = (int) point.getX();
				int y = (int) point.getY();
				g.setColor(Color.RED);
				g.setStroke(new BasicStroke(2));
				g.draw(new Rectangle2D.Float(x - 6, y - 6, 12, 12));
			}
		};

		// Um CompoundPainter permite combinar vários painters ao mesmo tempo...
		CompoundPainter cp = new CompoundPainter();
		cp.setPainters(pontosPainter, linhasPainter, guiPainter);

		jXMapKit.getMainMap().setOverlayPainter(cp);

		selCentro = null;
		linhas = new ArrayList<Tracado>();
	}

	/*
	 * Informa o nível máximo de zoom para exibir os labels dos pontos
	 * 
	 * @param nível máximo
	 */
	public void setMaxZoomText(int max) {
		maxZoomText = max;
	}

	/*
	 * Informa a localização de um ponto
	 * 
	 * @param ponto central
	 */
	public void setPosicao(GeoPosition sel) {
		this.selCentro = sel;
	}
	
	/*
	 * Informa se desejamos desenhar as linhas
	 * com arcos (true) ou linhas comuns (false)
	 */
	public void setArcos(boolean g) {
		this.useGeodesic = true;
	}

	/*
	 * Retorna a localização de um ponto
	 * 
	 * @returns ponto selecionado
	 */
	public GeoPosition getPosicao() {
		return selCentro;
	}

	/*
	 * Informa os pontos a serem desenhados (precisa ser chamado a cada vez que
	 * mudar)
	 * 
	 * @param lista lista de pontos (objetos MyWaypoint)
	 */
	public void setPontos(List<MyWaypoint> lista) {
		// Criando um conjunto de pontos
		Set<MyWaypoint> pontos = new HashSet<MyWaypoint>(lista);
		// Informando o conjunto ao painter
		pontosPainter.setWaypoints(pontos);
	}

	/*
	 * Adiciona um novo traçado ao mapa (o traçado tem um conjunto de pontos e
	 * uma cor)
	 */
	public void addTracado(Tracado tr) {
		if(!useGeodesic)
		{
			linhas.add(tr);
			return;
		}			
		Geodesic geod = Geodesic.WGS84;
		Tracado novo = new Tracado();
		novo.setWidth(tr.getWidth());
		novo.setCor(tr.getCor());
		novo.setLabel(tr.getLabel());
		ArrayList<Geo> pontos = tr.getPontos();
		for (int pos = 0; pos < pontos.size() - 1; pos++) {
			double lat1 = pontos.get(pos).getLatitude();
			double lat2 = pontos.get(pos + 1).getLatitude();
			double lon1 = pontos.get(pos).getLongitude();
			double lon2 = pontos.get(pos + 1).getLongitude();
						
//			if(Math.abs(lon1-lon2)>180) continue;
			GeodesicLine line = geod.InverseLine(lat1, lon1, lat2, lon2,
					GeodesicMask.DISTANCE_IN | GeodesicMask.LATITUDE | GeodesicMask.LONGITUDE);
			double ds0 = 200e3; // Nominal distance between points = 200 km
			// The number of intervals
			int num = (int) (Math.ceil(line.Distance() / ds0));
			// Slightly faster, use intervals of equal arc length
			double da = line.Arc() / num;			
			for (int i = 0; i <= num; ++i) {
				GeodesicData g = line.ArcPosition(i * da, GeodesicMask.LATITUDE | GeodesicMask.LONGITUDE);
				if(Math.abs(g.lon1-g.lon2)>180) continue;
				novo.addPonto(new Geo(g.lat2, g.lon2));
				//System.out.println(i + " " + g.lat2 + " " + g.lon2);
			}
		}		
		linhas.add(novo);
	}

	/*
	 * Limpa os traçados atuais
	 */
	public void clear() {
		linhas.clear();
	}

	/*
	 * Retorna o total de traçados
	 */
	public int totalTracados() {
		return linhas.size();
	}

	/*
	 * Retorna a referência ao objeto JXMapKit, para ajuste de parâmetros (se
	 * for o caso)
	 * 
	 * @returns referência para objeto JXMapKit em uso
	 */
	public JXMapKit getMapKit() {
		return jXMapKit;
	}

}
