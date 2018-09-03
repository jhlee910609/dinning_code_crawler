import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;

import java.util.Scanner;

public class Sample {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in);

		String str = "";

		System.out.print("input keyword> ");

		String keyword = sc.nextLine();

		sc.close();

		/* 10 chunks * 10 restaurants = 100 restaurants in 1 keyword */
		for (int i = 1; i <= 10; i++) {
			Document doc = Jsoup.connect("http://www.diningcode.com/list.php?page=" + i + "&chunk=10&query=" + keyword)
					.get();

			Element searchList = doc.getElementById("search_list");

			Elements restaurants = searchList.getElementsByTag("dc-restaurant");

			for (int j = 0; j < 10; j++) {
				Element rest = restaurants.get(j);

				// name
				str = str.concat(
						"\"" + rest.getElementsByClass("dc-restaurant-name").get(0).getElementsByTag("a").get(0).html()
								+ "\",");
				// category
				str = str.concat("\"" + rest.getElementsByClass("dc-restaurant-category").get(0).html() + "\",");
				// tag
				str = str.concat("\"" + rest.getElementsByClass("dc-restaurant-info-text").get(0).html() + "\",");
				// address
				str = str.concat("\"" + rest.getElementsByClass("dc-restaurant-info-text").get(1).html() + "\",");
				// contact
				str = str.concat("\"" + rest.getElementsByClass("dc-restaurant-info-text").get(2).html() + "\",");
				// 20 photos
				str = str.concat(rest.getElementsByClass("dc-restaurant-photo-container").get(0)
						.getElementsByTag("dc-rimage").get(0).attr("data-image") + "\n");

				// print restaurant name
				System.out
						.print(rest.getElementsByClass("dc-restaurant-name").get(0).getElementsByTag("a").get(0).html()
								+ ", ");
			}
			System.out.println();
		}

		// write all informations to keword.csv
		FileWriter fw = null;
		fw = new FileWriter("./" + keyword + ".csv");
		fw.write(str);
		if (fw != null) {
			fw.close();
		}

		System.out.println("keyword " + keyword + ": Search End...");
	}
}
