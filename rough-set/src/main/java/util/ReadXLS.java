package util;
import java.io.*; 
import java.sql.*; 
import jxl.*; 
public class ReadXLS { 
	private String fileName; 
	public ReadXLS(String fileName){ 
		this.fileName = fileName; 
	} 
	public void updateDb(){ 
		try{ 
			Connection conn = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xlsx)};DBQ=" + fileName);//DbPool.connectDB(); 
			if(conn != null){ 
				Statement stmt = conn.createStatement(); 
				jxl.Workbook rwb = null; 
				try{ 

					InputStream is = new FileInputStream(fileName); 
					rwb = Workbook.getWorkbook(is); 

					Sheet rs = rwb.getSheet(0); 
					int rsColumns = rs.getColumns();
					int rsRows = rs.getRows();

					String simNumber = "";
					String termSeqId = ""; 

					for(int i=0;i<rsRows;i++){
						for(int j=0;j<rsColumns;j++){ 
							Cell cell = rs.getCell(j,i); 
							if(j==0){ 
								simNumber = cell.getContents();
							} 
							if(j == 1){ 
								termSeqId = cell.getContents(); 
							} 
						} 
						String sql = "update ....";//SQL
						int isOk = stmt.executeUpdate(sql); 
						if(isOk == 0){ 
							String insertSql = "insert....";//SQL
							int isAdd = stmt.executeUpdate(insertSql); 
							if(isAdd > 0){ 
								System.out.println("Successfully inserted the "+i+"th item");
							} 

						} 

					} 
				}catch(Exception e){ 
					e.printStackTrace(); 
				} 
				finally{ 
					rwb.close(); 

				} 
			} 
		}catch(Exception e){ 
			e.printStackTrace(); 
		} 
	} 



}