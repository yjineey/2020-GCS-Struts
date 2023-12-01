package com.util;

public class MyUtil {

	public Object pageIndexList(int currentPage, int totalPage, String urlList) {
		// 페이징을 저장알 변수
		StringBuffer sb = new StringBuffer();

		int numPerBlock = 10; // 페이징 리스트 몇개씩 보여줄지
		int currentPageSetup; // 현재 페이지에 맞춰서
		int page;
		int prev;
		int next;

		if (currentPage == 0 && totalPage <= 0) // 데이터가 없거나 전체 페이지수가 1페이지를 못채우면 페이징x
		{
			return "";
		}

		// 검색값이 있으면 이미 request 값에 searchKey 값과 value 값이 있으므로 &을 붙여 줘야 한다.
		if (urlList.indexOf("?") != -1) {
			urlList += "&";
		} else {
			urlList += "?";
		}

		// currentPageSetup : 표시할페이지 -1
		currentPageSetup = (currentPage / numPerBlock) * numPerBlock;
		if (currentPage % numPerBlock == 0)// 만약 위에서 current_page 가 20이였다면 20/10=2 *10 하면 20이되는데 이럴경우 다시 10을 빼서 10으로 만듬
		{
			currentPageSetup = currentPageSetup - numPerBlock;
		}

		// 이전으로
		prev = ((((currentPage / numPerBlock) - 1) * numPerBlock) + 1);

		// currentPageSetup 0보다 큰경우는 이미 10이상이기 때문이다. 이때 current 페이지가 11페이지 이상일경우 앞에
		if (totalPage > numPerBlock && currentPageSetup > 0) {
			// sb.append("<a href='" + urlList + "pageNum=1'>1</a>&nbsp;");// 1
			sb.append("&nbsp;[<a href='" + urlList + "pageNum=" + prev + "'>prev</a>]&nbsp;");
		}

		// next, prev 이동시 +1 해줌으로서 10부터 시작하지 않고 11부터 시작
		page = currentPageSetup + 1;
		while (page <= totalPage && page <= currentPageSetup + numPerBlock) { // 페이징 리스트 나열 page++
			if (page == currentPage) {
				sb.append("<font color='Fuchsia'>" + page + "</font>&nbsp;");// 현재 페이지는 번호는 색깔로 표시함
			} else {
				sb.append("<a href='" + urlList + "pageNum=" + page + "'>" + page + "</a>&nbsp;");
			}
			page++;
		}

		// 다음으로
		next = ((((currentPage / numPerBlock) + 1) * numPerBlock) + 1);

		if (totalPage - currentPageSetup > numPerBlock) {
			// 전체 페이지가 16이라고 하고 현재 페이지가 6이면 currentPageSetup 은 0이므로 16-0=16이다
			// numperBlock 이 10이라 가정하면 조건이 참이므로 현제 페이지에서는 [next] 페이지와 마지막 페이지가 나온다.
			// sb.append("<a href='" + urlList + "pageNum=" + totalPage + "'>" + totalPage +
			// "</a>");
			sb.append("&nbsp;[<a href='" + urlList + "pageNum=" + next + "'>next</a>]&nbsp;");
		}
		return sb.toString();
	}

	public int getPageCount(int numPerPage, int totalDataCount) {
		int totalPage = 0;
		totalPage = (int) Math.ceil(totalDataCount / numPerPage);
		if (totalDataCount % numPerPage != 0) {
			totalPage += 1;
		}
		return totalPage;
	}

}
