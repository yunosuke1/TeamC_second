package com.example.ecommerce_a.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.ecommerce_a.domain.Item;
@SpringBootTest
class ItemRepositoryTest {
	
	@Autowired
	private ItemRepository repository;

	/**
	 * 価格の安い順で全件検索を行います。
	 * 最初と最後のデータである
	 * チョコクッキー,エスプレッソフラペチーノを対象に
	 * ２パターン(IDとName)と
	 * List のsizeを検証
	 */
	@Test
	void testFindAll() {
		//オブジェクト生成
		Item item=new Item();
		Item item2=new Item();
		//期待値
		item.setId(4);
		item.setName("チョコクッキー");
		item.setPriceM(190);
		item.setPriceL(300);
		item.setImagePath("4.jpg");
		
		item2.setId(2);
		item2.setName("エスプレッソフラペチーノ");
		
		//実測値
		List<Item>	itemList=repository.findAll();
		//テスト
		assertTrue(itemList.size()>0);
		assertEquals(itemList.get(0).getId(), item.getId());
		assertEquals(itemList.get(0).getName(), item.getName());
		assertEquals(itemList.get(17).getId(), item2.getId());
		assertEquals(itemList.get(17).getName(), item2.getName());
						
	}

	/**
	 * 価格の高い順で全件検索を行います。
	 * 最初と最後のデータである
	 * チョコクッキー,エスプレッソフラペチーノを対象に
	 * ２パターン(IDとName)と
	 * List のsizeを検証
	 */
	@Test
	void testFindAllDesc() {
		//オブジェクト生成
		Item item=new Item();
		Item item2=new Item();
		//期待値
		item.setId(4);
		item.setName("チョコクッキー");
		item.setPriceM(190);
		item.setPriceL(300);
		item.setImagePath("4.jpg");
		
		item2.setId(2);
		item2.setName("エスプレッソフラペチーノ");
		
		//実測値
		List<Item>	itemList=repository.findAllDesc();
		//テスト
		assertTrue(itemList.size()>0);
		assertEquals(itemList.get(17).getId(), item.getId());
		assertEquals(itemList.get(17).getName(), item.getName());
		assertEquals(itemList.get(0).getId(), item2.getId());
		assertEquals(itemList.get(0).getName(), item2.getName());
	}

	/**
	 * 「チ」で検索し、
	 * 最初と最後のデータである
	 * チョコクッキー,エスプレッソフラペチーノを対象に
	 * ２パターン(IDとName)と
	 * List のsizeを検証
	 */
	@Test
	void testFindByLikeName() {
		//オブジェクト生成
		Item item=new Item();
		Item item2=new Item();
		//期待値
		item.setId(4);
		item.setName("チョコクッキー");
		item.setPriceM(190);
		item.setPriceL(300);
		item.setImagePath("4.jpg");
		
		item2.setId(2);
		item2.setName("エスプレッソフラペチーノ");
		
		//実測値
		List<Item>	itemList=repository.findByLikeName("チ");
		//テスト
		assertTrue(itemList.size()>0);
		assertEquals(itemList.get(0).getId(), item.getId());
		assertEquals(itemList.get(0).getName(), item.getName());
		assertEquals(itemList.get(6).getId(), item2.getId());
		assertEquals(itemList.get(6).getName(), item2.getName());
		
		
		
	}
	/**
	 * 「チ」で検索し、
	 * 最初と最後のデータである
	 * チョコクッキー,エスプレッソフラペチーノを対象に
	 * ２パターン(IDとName)と
	 * List のsizeを検証
	 */
	@Test
	void testFindByLikeNameDesc() {
		//オブジェクト生成
		Item item=new Item();
		Item item2=new Item();
		//期待値
		item.setId(4);
		item.setName("チョコクッキー");
		item.setPriceM(190);
		item.setPriceL(300);
		item.setImagePath("4.jpg");
		
		item2.setId(2);
		item2.setName("エスプレッソフラペチーノ");
		
		//実測値
		List<Item>	itemList=repository.findByLikeNameDesc("チ");
		//テスト
		assertTrue(itemList.size()>0);
		assertEquals(itemList.get(6).getId(), item.getId());
		assertEquals(itemList.get(6).getName(), item.getName());
		assertEquals(itemList.get(0).getId(), item2.getId());
		assertEquals(itemList.get(0).getName(), item2.getName());
		
		
		
	}

	/**
	 * チョコクッキーの呼び出しで
	 * ２パターン(IDとName)を検証
	 */
	@Test
	void testLoad() {
		//オブジェクト生成
		Item item=new Item();
		//期待値
		item.setId(4);
		item.setName("チョコクッキー");
		item.setPriceM(190);
		item.setPriceL(300);
		item.setImagePath("4.jpg");
		//実測値
		Item expected=repository.load(4);
		//テスト
		assertEquals(item.getId(), expected.getId());
		assertEquals(item.getName(), expected.getName());
		
	}

}
