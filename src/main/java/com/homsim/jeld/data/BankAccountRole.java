package com.homsim.jeld.data;

/**
 * Database-mapping is supposed to be ORDINAL, meaning:
 * <table>
 *   <tr>
 *     <th> Domain model </th> <th> DB model </th>
 *   </tr>
 *   <tr>
 *     <td> USERACCOUNT </td> <td> 0 </td>
 *   </tr>
 *   <tr>
 *     <td> COUNTERPARTY </td> <td> 1 </td>
 *   </tr>
 * </table>
 */
public enum BankAccountRole {
    USERACCOUNT,
    COUNTERPARTY
}
