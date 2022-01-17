/*
 * @description: 
 * @Author: vikingShip
 * @Date: 2021-08-31 18:02:12
 * @LastEditors: vikingShip
 * @LastEditTime: 2021-08-31 18:06:21
 */
export interface CheckForm{
    phone:string | number | null
    code:string | number | null
    oneCertificate?:string | number | null
    twoCertificate?:string | number | null
    buttonName: string
    isDisabled: boolean
    time: number
}