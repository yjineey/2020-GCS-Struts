package com.boardTest;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.util.MyUtil;
import com.boardTest.BoardForm;
import com.util.dao.CommonDAO;
import com.util.dao.CommonDAOImpl;

public class BoardAction extends DispatchAction {

	// 게시글 작성페이지
	public ActionForward created(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonDAO dao = CommonDAOImpl.getInstance();
		String mode = request.getParameter("mode");
		
		// insert
		if (mode == null) {
			request.setAttribute("mode", "save");
		} else {
			// update
			int num = Integer.parseInt(request.getParameter("num"));
			String pageNum = request.getParameter("pageNum");

			// 수정시 필요한 정보 가져오기
			BoardForm dto = (BoardForm) dao.getReadData("boardTest.readData", num);
			if (dto == null) {
				return mapping.findForward("list");
			}
			request.setAttribute("dto", dto);
			request.setAttribute("mode", "updateok");
			request.setAttribute("pageNum", pageNum);
		}
		return mapping.findForward("created");
	}

	public ActionForward created_ok(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonDAO dao = CommonDAOImpl.getInstance();
		BoardForm f = (BoardForm) form;
		// mode로 글작성인지 수정인지 표시
		String mode = request.getParameter("mode");

		// 글작성
		if (mode.equals("save")) {
			int maxNum = dao.getIntValue("boardTest.maxNum");
			f.setNum(maxNum + 1);
//			f.setIpAddr(request.getRemoteAddr());
			dao.insertData("boardTest.insertData", f);

			// 글수정
		} else {
			String pageNum = request.getParameter("pageNum");
			dao.updateData("boardTest.updateData", f);

//			HttpSession session = request.getSession();
//			session.setAttribute("pageNum", 1);
		}
		dao = null;
		return mapping.findForward("created_ok");
	}

	// 게시판 리스트조회시
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// commonDAOImpl를 dao로 사용
		CommonDAO dao = CommonDAOImpl.getInstance();
		// 절대경로는 cp
		String cp = request.getContextPath();
		MyUtil myUtil = new MyUtil();
		// 한페이지에 표시되는 게시글수
		int numPerPage = 5;
		// 전체 페이지수
		int totalPage = 0;
		// 전체 데이터 수
		int totalDataCount = 0;

		// pagenum값을 받아서 pageNum이라고 하겠다
		String pageNum = request.getParameter("pageNum");
		// 현제 페이지는 1페이지 지정
		int currentPage = 1;

		// 글작성 페이지에서 넘어올 경우에는 페이지 넘 값이 없다
		HttpSession session = request.getSession();
		if (pageNum == null) {
			pageNum = (String) session.getAttribute("pageNum");
		}

		// 리스트페이지 보기
		if (pageNum != null) {
			// 페이지수가 없으면 1페이지
			currentPage = Integer.parseInt(pageNum);
		}

		// 검색에서 사용한 searchKey
		String searchKey = request.getParameter("searchKey");
		// searchValue 검색내용
		String searchValue = request.getParameter("searchValue");

		// 게시글작성에서 넘어와 값이 없을 경우
//		if (searchValue == null) {
//			searchKey = (String) session.getAttribute("searchKey");
//			searchValue = (String) session.getAttribute("searchValue");
//		}
//		session.removeAttribute("searchKey");
//		session.removeAttribute("searchValue");

		// 검색내용이 없으면
		if (searchValue == null) {
			searchKey = "subject";
			searchValue = "";
		}

		// get타입으로, utf-8형식으로 searchkey
		if (request.getMethod().equalsIgnoreCase("GET")) {
			searchKey = URLDecoder.decode(searchKey, "UTF-8");
		}

		Map<String, Object> hMap = new HashMap<String, Object>();
		// searchkey = 검색조건
		hMap.put("searchKey", searchKey);
		// searchvalue = 검색내용
		hMap.put("searchValue", searchValue);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaa", "aaa");
		hMap.put("searchKey5", map);

		// hMap 값을 boardTest_sqlMap.xml의 dataCount에 넣고,
		// 그 값을 CommonDAOImpl의 getIntValue에 넣으면 totalDataCount!
		totalDataCount = dao.getIntValue("boardTest.dataCount", hMap);

		// 전체개수가 0이 아니면
		if (totalDataCount != 0) {
			// myUtil.getPageCount 코드추가
			totalPage = myUtil.getPageCount(numPerPage, totalDataCount);
		}
		// 현제페이지가>전체페이지수보다 크면
		if (currentPage > totalPage) {
			currentPage = totalPage;
		}

		// start = 페이지의 글번호 첫번호
		int start = (currentPage - 1) * numPerPage + 1;
		// end = 페이지의 끝번호
		int end = currentPage * numPerPage;

		hMap.put("start", start);
		hMap.put("end", end);

		// hMap에는 4개의 데이터 존재 (searchKey,searchValue,start,end)
		List<Object> lists = dao.getListData("boardTest.listData", hMap);

		String param = "";
		String urlArticle = "";
		String urlList = "";

		// 검색내용이 있다면
		if (!searchValue.equals("")) {
			searchValue = URLEncoder.encode(searchValue, "UTF-8");
			// 검색조건,내용
			param = "&searchKey=" + searchKey + "&searchValue=" + searchValue;
		}

		urlList = cp + "/boardTest.do?method=list" + param;
		urlArticle = cp + "/boardTest.do?method=article&pageNum=" + currentPage + param;

		request.setAttribute("lists", lists);
		// 경로 + 현재페이지 + 검색조건 + 검색내용
		request.setAttribute("urlArticle", urlArticle);
		// 페이지번호
		request.setAttribute("pageNum", currentPage);
		// 현재페이지,전체페이지,list
		request.setAttribute("pageIndexList", myUtil.pageIndexList(currentPage, totalPage, urlList));
		// 전체 데이터수
		request.setAttribute("totalDataCount", totalDataCount);
		// 전체페이지수
		request.setAttribute("totalPage", totalPage);

		return mapping.findForward("list");

	}

	// 게시물상세보기
	public ActionForward article(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		CommonDAO dao = CommonDAOImpl.getInstance();
		String cp = request.getContextPath();

		// 게시물 번호, 페이지번호, 검색조건, 검색내용을 받아서 선언
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");

		// 검색내용이 없으면 전체보기
		if (searchValue == null) {
			searchKey = "subject";
			searchValue = "";
		}

		if (request.getMethod().equalsIgnoreCase("GET"))
			searchKey = URLDecoder.decode(searchKey, "UTF-8");

		// 게시글 볼떄 조회수 1씩 증가
		dao.updateData("boardTest.hitCountUpdate", num);

		// queryForObject = 하나의 데이터 가져오기
		// 반환형태가 object이므로 다운캐스팅 해줘야한다.
		BoardForm dto = (BoardForm) dao.getReadData("boardTest.readData", num);

		// dto의 값이 없으면 list화면을 보여준다
		if (dto == null)
			return mapping.findForward("list");
//		int lineSu = dto.getContent().split("\n").length;
//		dto.setContent(dto.getContent().replaceAll("\n", "<br/>"));

		// 이전글 다음글
		String preUrl = "";
		String nextUrl = "";

		// boardTest_sqlMpa.xml에서 매개변수를 map으로 받아, map 생성
		Map<String, Object> hMap = new HashMap<String, Object>();
		hMap.put("searchKey", searchKey);
		hMap.put("searchValue", searchValue);
		hMap.put("num", num);

		// 이전 게시물 정보가져온다
		String preSubject = "";
		BoardForm preDTO = (BoardForm) dao.getReadData("boardTest.preReadData", hMap);
		if (preDTO != null) {
			preUrl = cp + "/boardTest.do?method=article&pageNum=" + pageNum + "&num=" + preDTO.getNum();
			preSubject = preDTO.getSubject();
		}

		// 다음 게시물 정보
		String nextSubject = "";
		BoardForm nextDTO = (BoardForm) dao.getReadData("boardTest.nextReadData", hMap);
		if (nextDTO != null) {
			nextUrl = cp + "/boardTest.do?method=article&pageNum=" + pageNum + "&num=" + nextDTO.getNum();
			nextSubject = nextDTO.getSubject();
		}

		// 수정과 삭제에서 사용할 인수
		String urlList = cp + "/boardTest.do?method=list&pageNum=" + pageNum;
		String paramArticle = "num=" + num + "&pageNum=" + pageNum;
		if (!searchValue.equals("")) {
			searchValue = URLEncoder.encode(searchValue, "UTF-8");
			// 나중에 article에서 리스트로 돌아갈 때 사용

			urlList += "&searchKey=" + searchKey + "&searchValue=" + searchValue;
			if (!preUrl.equals("")) {
				preUrl += "&searchKey=" + searchKey + "&searchValue=" + searchValue;
			}

			if (!nextUrl.equals("")) {
				nextUrl += "&searchKey=" + searchKey + "&searchValue=" + searchValue;
			}
			paramArticle += "&searchKey=" + searchKey + "&searchValue=" + searchValue;
			;
		}
		request.setAttribute("dto", dto);
		request.setAttribute("preSubject", preSubject);
		request.setAttribute("preUrl", preUrl);
		request.setAttribute("nextSubject", nextSubject);
		request.setAttribute("nextUrl", nextUrl);
//		request.setAttribute("lineSu", lineSu);
		request.setAttribute("paramArticle", paramArticle);
		request.setAttribute("urlList", urlList);
		return mapping.findForward("article");

	}

	public ActionForward deleted(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		CommonDAO dao = CommonDAOImpl.getInstance();
		String cp = request.getContextPath();

		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");

		dao.deleteData("boardTest.deleteData", num);

		HttpSession session = request.getSession();
		session.setAttribute("pageNum", pageNum);
		session.setAttribute("searchKey", searchKey);
		session.setAttribute("searchValue", searchValue);

		return mapping.findForward("deleted_ok");
	}

}