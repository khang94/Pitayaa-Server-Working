package pitayaa.nail.msg.business.util.common;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pitayaa.nail.msg.business.exception.BusinessException;
import pitayaa.nail.msg.business.exception.ParseTransactionMessageException;

public class QueryUtils {
	private final static int MAX_LENGTH_OPERATOR = 4;

	private static String getDataType(String table_name, String column_name) {
		String result = "";
		TableType tableType = TableType.getInstance();

		HashMap<String, HashMap<String, String>> hm = tableType.getHashMap();
		HashMap<String, String> typeHm = hm.get(table_name.toUpperCase());

		result = typeHm.get(column_name.toUpperCase());

		return result;
	}

	public static String parseSqlFromJSonObject(JSONObject object)
			throws JSONException, BusinessException {
		StringBuilder sqlString = new StringBuilder("");

		try {
			String type = object.getString("type").toUpperCase();
			String tableName = object.getString("tableName");
			tableName = tableName.toUpperCase();
			HashMap<String, String> mapTable = TableType.getInstance()
					.getHashMap().get(tableName);

			// kiem tra table khong nam trong danh sach cho truoc.
			if (null == mapTable) {
				throw new BusinessException(
						"Invalidate table name. Table name: " + tableName);
			}

			if (type.equals("INSERT")) {
				sqlString.append("insert into " + tableName + " ");
				StringBuilder column = new StringBuilder("(");
				StringBuilder valuesParam = new StringBuilder("(");
				JSONArray listParams = object.getJSONArray("listParam");

				for (int i = 0, length = listParams.length(); i < length; i++) {
					JSONObject objectParams = listParams.getJSONObject(i);
					String columnName = objectParams.getString("column")
							.toUpperCase();

					// kiem tra column khong nam trong danh sach column cuar
					// table cho truoc.
					if (null == mapTable.get(columnName)) {
						throw new BusinessException(String.format(
								"Invalidate column %s for table %s.",
								columnName, tableName));
					}

					column.append(columnName);
					String objectType = "";

					if (objectParams.has("type")) {
						objectType = objectParams.getString("type")
								.toUpperCase();
					}
					if (objectType != null && objectType.equals("SYSDATE")) {
						valuesParam.append("now()");
					} else if (objectType != null
							&& objectType.equals("SEQUENCE")) {
						String seqName = objectParams.getString("value")
								.toUpperCase();

						if (!TableType.getInstance().getListSeqName()
								.contains(seqName)) {
							throw new BusinessException(String.format(
									"Invalidate sequence name %s.", seqName));
						}

						valuesParam.append("NEXTVAL('" + seqName + "')");
					} else if (objectType != null && objectType.equals("NULL")) {
						valuesParam.append("null");
					} else {
						valuesParam.append("?");// object_params.getString("value");
					}

					if (i < length - 1) {
						column.append(", ");
						valuesParam.append(", ");
					}
				}

				column.append(")");
				valuesParam.append(")");
				sqlString.append(column.toString() + " values "
						+ valuesParam.toString());
			} else if (type.equals("UPDATE")) {
				// update log set value = ?, params = ? where log_id = ?
				sqlString.append("update " + tableName + " set ");
				StringBuilder column = new StringBuilder("");
				JSONArray listParams;

				try {
					listParams = object.getJSONArray("listParam");
				} catch (Exception ex) {
					listParams = new JSONArray();
				}

				for (int i = 0, length = listParams.length(); i < length; i++) {
					JSONObject objectParams = listParams.getJSONObject(i);
					// truong hop la key thi bo qua khong update

					if (objectParams.has("key")) {
						String key_type = objectParams.getString("key")
								.toUpperCase();
						if (key_type.equals("ISPK")) {
							if (i == length - 1) {
								column = column.replace(column.length() - 2,
										column.length(), "");
							}

							continue;
						}
					}

					String columnName = objectParams.getString("column")
							.toUpperCase();

					// table cho truoc.
					if (null == mapTable.get(columnName)) {
						throw new BusinessException(String.format(
								"Invalidate column %s for table %s.",
								columnName, tableName));
					}

					column.append(columnName);
					column.append("=");
					String objectType = "";

					if (objectParams.has("type")) {
						objectType = objectParams.getString("type")
								.toUpperCase();
					}
					if (objectType.equals("SYSDATE")) {
						column.append("CURRENT_TIMESTAMP");
					} else if (objectType.equals("SEQUENCE")) {
						String seqName = objectParams.getString("value")
								.toUpperCase();

						if (!TableType.getInstance().getListSeqName()
								.contains(seqName)) {
							throw new BusinessException(String.format(
									"Invalidate sequence name %s.", seqName));
						}

						column.append("NEXTVAL('" + seqName + "')");
					} else if (objectType.equals("NULL")) {
						column.append("null");
					} else if (objectType.equals("OPERATION")) {
						// neu la toan tu thi add thang du lieu luon, khong qua
						// params
						String value = objectParams.getString("value")
								.substring(0, 2);
						if (value.equals("*-")) {
							column.append(columnName);
							/*
							 * column.append(" - " +
							 * object_params.getString("value") .substring(2));
							 */
							column.append(" - ? ");
						} else if (value.equals("*+")) {
							column.append(columnName);
							/*
							 * column.append(" + " +
							 * object_params.getString("value") .substring(2));
							 */
							column.append(" + ? ");
						} else if (value.equals("*/")) {
							column.append(columnName);
							/*
							 * column.append(" / " +
							 * object_params.getString("value") .substring(2));
							 */
							column.append(" / ? ");
						} else if (value.equals("**")) {
							column.append(columnName);
							/*
							 * column.append(" * " +
							 * object_params.getString("value") .substring(2));
							 */
							column.append(" * ? ");
						}
					} else {
						column.append("?");
					}
					if (i < length - 1) {
						column.append(", ");
					}
				}

				sqlString.append(column.toString());
				JSONArray where_params = object.getJSONArray("listWhereParam");
				StringBuilder whereString = new StringBuilder("");

				for (int j = 0, wlength = where_params.length(); j < wlength; j++) {
					JSONObject objectParams = where_params.getJSONObject(j);
					String columnName = objectParams.getString("column")
							.toUpperCase();
					// table cho truoc.
					if (null == mapTable.get(columnName)) {
						throw new BusinessException(String.format(
								"Invalidate column %s for table %s.",
								columnName, tableName));
					}

					whereString.append(columnName);
					String operator = "=";
					if (objectParams.has("operator")) {
						operator = objectParams.getString("operator");

						if (operator.length() > MAX_LENGTH_OPERATOR) {
							throw new BusinessException(String.format(
									"Invalidate operator: %s.", operator));
						}
					}
					whereString.append(operator);
					whereString.append("?");
					if (j < wlength - 1) {
						whereString.append(" and ");
					}
				}

				sqlString.append(" where " + whereString.toString());
			} else if (type.equals("DELETE")) {
				sqlString.append("delete from " + tableName);
				JSONArray whereParams = object.getJSONArray("listWhereParam");
				StringBuilder whereString = new StringBuilder("");

				for (int j = 0, wlength = whereParams.length(); j < wlength; j++) {
					JSONObject objectParams = whereParams.getJSONObject(j);
					String objectType = "";
					String columnName = objectParams.getString("column")
							.toUpperCase();
					// table cho truoc.
					if (null == mapTable.get(columnName)) {
						throw new BusinessException(String.format(
								"Invalidate column %s for table %s.",
								columnName, tableName));
					}

					if (objectParams.has("type")) {
						objectType = objectParams.getString("type")
								.toUpperCase();
					}
					// kiem tra xem co phai du lieu ngay khong de check dk
					// trunc(ngay)
					if (objectType.equals("TRUNC")) {
						whereString.append(" date_trunc(");
						whereString.append(columnName);
						whereString.append(")");
					} else {
						whereString.append(columnName);
					}
					String operator = "=";
					if (objectParams.has("operator")) {
						operator = objectParams.getString("operator");
					}
					whereString.append(operator);
					if (objectType.equals("TRUNC")) {
						whereString.append(" date_trunc(");
						whereString.append("?");
						whereString.append(")");
					} else {
						whereString.append("?");
					}
					if (j < wlength - 1) {
						whereString.append(" and ");
					}
				}

				sqlString.append(" where " + whereString.toString());
			}
		} catch (JSONException e) {
			throw e;
		}

		return sqlString.toString();
	}

	private static Integer getLengthParams(JSONObject object) {

		int result = 0;
		JSONArray listParams = null;
		try {
			listParams = object.getJSONArray("listParam");
		} catch (Exception ex) {
			listParams = new JSONArray();
		}
		try {
			String typeStatement = object.getString("type").toUpperCase();
			for (int i = 0, length = listParams.length(); i < length; i++) {
				JSONObject objectParams;
				objectParams = listParams.getJSONObject(i);
				// bo qua truong hop key khong add vao params luc update
				if (objectParams.has("key") && typeStatement.equals("UPDATE")) {
					String keyType = objectParams.getString("key")
							.toUpperCase();
					if (keyType.equals("ISPK")) {
						continue;
					}
				}
				String objectType = "";
				if (objectParams.has("type")) {
					objectType = objectParams.getString("type").toUpperCase();
				}
				if (objectType.equals("SYSDATE")
						|| objectType.equals("SEQUENCE")
						/*
						 * || (object_type.equals("OPERATION") && type_statement
						 * .equals("UPDATE"))
						 */
						|| objectType.equals("NULL")) {
				} else {
					result++;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		// add them listwhereParam
		if (object.has("listWhereParam")) {
			JSONArray listWhereParams;
			try {
				listWhereParams = object.getJSONArray("listWhereParam");
				result += listWhereParams.length();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static Object[] parseParamsSqlFromJSonObject(JSONArray arrayDeclare,
			JSONObject object, Object[] values) throws JSONException,
			ParseException, ParseTransactionMessageException {
		Object paramsObject[] = null;
		JSONObject objectDeclare;
		String declare[] = new String[arrayDeclare.length()];

		for (int i = 0, length = arrayDeclare.length(); i < length; i++) {
			try {
				objectDeclare = arrayDeclare.getJSONObject(i);
				declare[i] = objectDeclare.getString("name");
			} catch (JSONException e) {
				throw e;
			}
		}

		try {
			JSONArray listParams = null;

			try {
				listParams = object.getJSONArray("listParam");
			} catch (Exception ex) {
				listParams = new JSONArray();
			}

			String typeStatement = object.getString("type").toUpperCase();
			String tableName = object.getString("tableName");

			paramsObject = new Object[getLengthParams(object)];

			int index = 0;

			for (int i = 0, length = listParams.length(); i < length; i++) {
				JSONObject objectParams = listParams.getJSONObject(i);
				String columnName = objectParams.getString("column");
				String valuesParam = objectParams.getString("value");

				Object objectParam = null;
				boolean checkDeclare = false;

				for (int j = 0, jlength = declare.length; j < jlength; j++) {
					String declereStringDetail = declare[j];
					if (valuesParam.equals(declereStringDetail)) {
						objectParam = values[j];
						checkDeclare = true;
						break;
					}
				}

				// bo qua truong hop key khi goi update
				if (objectParams.has("key") && typeStatement.equals("UPDATE")) {
					String keyType = objectParams.getString("key")
							.toUpperCase();
					if (keyType.equals("ISPK")) {
						continue;
					}
				}

				String objectType = "";
				if (objectParams.has("type")) {
					objectType = objectParams.getString("type").toUpperCase();
				}

				if (objectType.equals("SYSDATE")
						|| objectType.equals("SEQUENCE")
						/*
						 * || (object_type.equals("OPERATION") &&
						 * type_statement.equals("UPDATE"))
						 */
						|| objectType.equals("NULL")) {
				} else {
					// truong hop insert ma co toan tu thi phai bo di 2 ky tu
					// dau tien
					if (objectType.equals("OPERATION")) {
						valuesParam = valuesParam.substring(2);
					}

					String type = getDataType(tableName, columnName);
					if (type.contains("SMALLINT")) {
						objectParam = new Short(valuesParam);
					} else if (checkDeclare == false) {
						if (type.contains("NUMBER") || type.contains("FLOAT")
								|| type.contains("INT")) {
							if (valuesParam.contains(".")) {
								objectParam = new BigDecimal(valuesParam);
							} else {
								objectParam = Long.parseLong(valuesParam);
							}
						} else if (type.contains("DATE")
								|| type.contains("TIME")) {
							try {
								if (valuesParam.length() == 10) {
									SimpleDateFormat sdf = new SimpleDateFormat(
											"yyyy-MM-dd");
									objectParam = sdf.parse(valuesParam);
								} else if (valuesParam.length() == 19) {
									SimpleDateFormat sdf = new SimpleDateFormat(
											"yyyy-MM-dd H:m:s");
									objectParam = sdf.parse(valuesParam);
								} else {
									throw new IllegalArgumentException();
								}
							} catch (IllegalArgumentException ex) {
								throw new ParseTransactionMessageException(
										"Wrong date format, require length 10 format yyyy-MM-dd or 19 yyyy-MM-dd  HH:mm:ss");
							}
						} else if (type.equals("FLOAT")) {
							objectParam = new BigDecimal(valuesParam);
						} else {
							objectParam = valuesParam;
						}
					}

					paramsObject[index] = objectParam;
					index++;
				}
			}

			// parse for listWhereParam
			if (object.has("listWhereParam")) {
				JSONArray whereParams = object.getJSONArray("listWhereParam");
				for (int j = 0, wlength = whereParams.length(); j < wlength; j++) {
					JSONObject object_params = whereParams.getJSONObject(j);
					String columnName = object_params.getString("column");
					String valuesParam = object_params.getString("value");
					Object objectParam = null;
					String type = getDataType(tableName, columnName);
					if (type.contains("SMALLINT")) {
						objectParam = new Short(valuesParam);
					} else if (type.contains("NUMBER")
							|| type.contains("FLOAT") || type.contains("INT")) {
						if (valuesParam.contains(".")) {
							objectParam = new BigDecimal(valuesParam);
						} else {
							objectParam = Long.parseLong(valuesParam);
						}
					} else if (type.equals("DATE")
							|| type.equals("TIMESTAMP(6)")) {
						try {
							if (valuesParam.length() == 10) {
								SimpleDateFormat sdf = new SimpleDateFormat(
										"yyyy-MM-dd");
								objectParam = sdf.parse(valuesParam);
							} else if (valuesParam.length() == 19) {
								SimpleDateFormat sdf = new SimpleDateFormat(
										"yyyy-MM-dd H:m:s");
								objectParam = sdf.parse(valuesParam);
							} else {
								throw new IllegalArgumentException();
							}
						} catch (IllegalArgumentException ex) {
							throw new ParseTransactionMessageException(
									"Wrong date format, require length 10 format yyyy-MM-dd or 19 yyyy-MM-dd  HH:mm:ss");
						}
					} else if (type.equals("FLOAT")) {
						objectParam = new BigDecimal(valuesParam);
					} else {
						objectParam = valuesParam;
					}
					paramsObject[index] = objectParam;
					index++;
				}
			}
		} catch (JSONException e) {
			throw e;
		} catch (ParseException e) {
			throw e;
		}
		return paramsObject;
	}

	public static Object[] parseParamsSqlFromJSonObject(JSONArray arrayDeclare,
			JSONObject object, Object[] values, String timezone)
			throws JSONException, ParseException,
			ParseTransactionMessageException, Exception {
		Object paramsObject[] = null;
		JSONObject objectDeclare;
		String declare[] = new String[arrayDeclare.length()];

		for (int i = 0, length = arrayDeclare.length(); i < length; i++) {
			try {
				objectDeclare = arrayDeclare.getJSONObject(i);
				declare[i] = objectDeclare.getString("name");
			} catch (JSONException e) {
				throw e;
			}
		}

		try {
			JSONArray listParams = null;

			try {
				listParams = object.getJSONArray("listParam");
			} catch (Exception ex) {
				listParams = new JSONArray();
			}

			String typeStatement = object.getString("type").toUpperCase();
			String tableName = object.getString("tableName");

			paramsObject = new Object[getLengthParams(object)];

			int index = 0;

			for (int i = 0, length = listParams.length(); i < length; i++) {
				JSONObject objectParams = listParams.getJSONObject(i);
				String columnName = objectParams.getString("column");
				String valuesParam = objectParams.getString("value");
				Object value = objectParams.get("value");
				if ("width".equalsIgnoreCase(columnName)) {
					System.out.println("debug");
				}

				Object objectParam = null;
				boolean checkDeclare = false;

				for (int j = 0, jlength = declare.length; j < jlength; j++) {
					String declereStringDetail = declare[j];
					if (valuesParam.equals(declereStringDetail)) {
						objectParam = values[j];
						checkDeclare = true;
						break;
					}
				}

				// bo qua truong hop key khi goi update
				if (objectParams.has("key") && typeStatement.equals("UPDATE")) {
					String keyType = objectParams.getString("key")
							.toUpperCase();
					if (keyType.equals("ISPK")) {
						continue;
					}
				}

				String objectType = "";
				if (objectParams.has("type")) {
					objectType = objectParams.getString("type").toUpperCase();
				}

				if (objectType.equals("SYSDATE")
						|| objectType.equals("SEQUENCE")
						/*
						 * || (object_type.equals("OPERATION") &&
						 * type_statement.equals("UPDATE"))
						 */
						|| objectType.equals("NULL")) {
				} else {
					// truong hop insert ma co toan tu thi phai bo di 2 ky tu
					// dau tien
					if (objectType.equals("OPERATION")) {
						valuesParam = valuesParam.substring(2);
					}

					String type = getDataType(tableName, columnName);// type="REAL"

					if (checkDeclare == false) {
						if (type.contains("SMALLINT")) {
							objectParam = new Short(valuesParam);
						} else if ((value instanceof Number)
								|| type.contains("NUMBER")
								|| type.contains("FLOAT")
								|| type.contains("INT")
								|| type.contains("DOUBLE")
								|| type.contains("REAL")
								|| type.contains("SERI")) {
							if (valuesParam.contains(".")) {
								objectParam = new BigDecimal(valuesParam);
							} else {
								objectParam = Long.parseLong(valuesParam);
							}
						} else if (type.contains("DATE")
								|| type.contains("TIME")) {
							try {
								if (valuesParam.length() == 10) {
									objectParam = DateUtil.convertToSysDate(
											valuesParam.trim() + " 00:00:00",
											timezone);
								} else if (valuesParam.length() == 19) {
									objectParam = DateUtil.convertToSysDate(
											valuesParam.trim(), timezone);
								} else {
									throw new IllegalArgumentException();
								}
							} catch (IllegalArgumentException ex) {
								throw new ParseTransactionMessageException(
										"Wrong date format, require length 10 format yyyy-MM-dd or 19 yyyy-MM-dd HH:mm:ss");
							}
						} else if (type.equals("FLOAT")) {
							objectParam = new BigDecimal(valuesParam);
						} else {
							objectParam = valuesParam;
						}
					}

					paramsObject[index] = objectParam;
					index++;
				}
			}

			// parse for listWhereParam
			if (object.has("listWhereParam")) {
				JSONArray whereParams = object.getJSONArray("listWhereParam");
				for (int j = 0, wlength = whereParams.length(); j < wlength; j++) {
					JSONObject object_params = whereParams.getJSONObject(j);
					String columnName = object_params.getString("column");
					Object valuesParam = object_params.get("value");
					String valueStr = String.valueOf(valuesParam);
					// Object objectParam = null;
					String type = getDataType(tableName, columnName);

					if (type.contains("NUMBER") || type.contains("FLOAT")
							|| type.contains("INT") || type.contains("REAL")
							|| type.contains("SERI")) {
						if (!(valuesParam instanceof Number)) {
							if (valueStr.contains(".")) {
								valuesParam = new BigDecimal(valueStr);
							} else {
								valuesParam = Long.parseLong(valueStr);
							}
						}

					} else if ((type.contains("DATE") || type.contains("TIME"))) {
						try {
							if (valueStr.length() == 10) {
								valuesParam = DateUtil
										.convertToSysDate(valueStr.trim()
												+ " 00:00:00", timezone);
							} else if (valueStr.length() == 19) {
								valuesParam = DateUtil.convertToSysDate(
										valueStr.trim(), timezone);
							} else {
								throw new IllegalArgumentException();
							}
						} catch (IllegalArgumentException ex) {
							throw new ParseTransactionMessageException(
									"Wrong date format, require length 10 format yyyy-MM-dd or 19 yyyy-MM-dd HH:mm:ss");
						}
					}

					paramsObject[index] = valuesParam;
					index++;
				}
			}
		} catch (JSONException e) {
			throw e;
		} catch (ParseException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
		return paramsObject;
	}
}
