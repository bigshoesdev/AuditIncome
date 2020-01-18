package com.system.software.solutions.sts.formbean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DailyAuditedFB {

	private Integer pcGrandTotal;
	private Integer lcdGrandTotal;
	private Integer ltGrandTotal;
	private List<DailyAudited> lcdList = new ArrayList<DailyAudited>();
	private List<DailyAudited> pcList = new ArrayList<DailyAudited>();
	private List<DailyAudited> ltList = new ArrayList<DailyAudited>();

	public DailyAudited getDailyAuditedNewInstance() {
		return new DailyAudited();
	}

	public Integer getPcGrandTotal() {
		return pcGrandTotal;
	}

	public void setPcGrandTotal(Integer pcGrandTotal) {
		this.pcGrandTotal = pcGrandTotal;
	}

	public Integer getLcdGrandTotal() {
		return lcdGrandTotal;
	}

	public List<DailyAudited> getPcList() {
		return pcList;
	}

	public void setPcList(List<DailyAudited> pcList) {
		this.pcList = pcList;
	}

	public List<DailyAudited> getLtList() {
		return ltList;
	}

	public void setLtList(List<DailyAudited> ltList) {
		this.ltList = ltList;
	}

	public void setLcdGrandTotal(Integer lcdGrandTotal) {
		this.lcdGrandTotal = lcdGrandTotal;
	}

	public Integer getLtGrandTotal() {
		return ltGrandTotal;
	}

	public void setLtGrandTotal(Integer ltGrandTotal) {
		this.ltGrandTotal = ltGrandTotal;
	}

	public List<DailyAudited> getLcdList() {
		return lcdList;
	}

	public void setLcdList(List<DailyAudited> lcdList) {
		this.lcdList = lcdList;
	}

	public class DailyAudited {
		private Date date;
		private Integer count;

		public DailyAudited() {
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public Integer getCount() {
			return count;
		}

		public void setCount(Integer count) {
			this.count = count;
		}

	}

}
