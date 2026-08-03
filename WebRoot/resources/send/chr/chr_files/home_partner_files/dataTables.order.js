//oSort��������������, 'chinese-asc'���Լ���������͵�����(*-asc || *-desc)���
//���Ӧ�û��ݱ���е����ݵ�����(string, number, chinese)���бȽ�����
//�����chinese��������������oSort['chinese-asc']��oSort['chinese-desc']�ķ���
//oSort��Ӧ��function�����Զ���ȽϷ���
jQuery.fn.dataTableExt.oSort['chinese-asc'] = function(x,y) {
 //javascript�Դ�����ıȽϺ�������÷������в����˽�
 return x.localeCompare(y);
};

jQuery.fn.dataTableExt.oSort['chinese-desc'] = function(x,y) {
 return y.localeCompare(x);
};

//aTypes�ǲ����ű���������͵�����
//reg��ֵ��������ʽ�������ж��Ƿ��������ַ�
//����ֵpush��aTypes���飬����ʱɨ������飬'chinese'�����������������������nullĬ����'string'
jQuery.fn.dataTableExt.aTypes.push(function(sData) {
 var reg =/^[\u4e00-\u9fa5]{0,}$/;
 if(reg.test(sData)) {
     return 'chinese';
 }
 return null;
});
/* Create an array with the values of all the input boxes in a column */
$.fn.dataTable.ext.order['dom-text'] = function  ( settings, col )
{
    return this.api().column( col, {order:'index'} ).nodes().map( function ( td, i ) {
        return $('input', td).val();
    } );
}
 
/* Create an array with the values of all the input boxes in a column, parsed as numbers */
$.fn.dataTable.ext.order['dom-text-numeric'] = function  ( settings, col )
{
    return this.api().column( col, {order:'index'} ).nodes().map( function ( td, i ) {
        return $('input', td).val() * 1;
    } );
}
 
/* Create an array with the values of all the select options in a column */
$.fn.dataTable.ext.order['dom-select'] = function  ( settings, col )
{
    return this.api().column( col, {order:'index'} ).nodes().map( function ( td, i ) {
        return $('select', td).val();
    } );
}
 
/* Create an array with the values of all the checkboxes in a column */
$.fn.dataTable.ext.order['dom-checkbox'] = function  ( settings, col )
{
    return this.api().column( col, {order:'index'} ).nodes().map( function ( td, i ) {
        return $('input', td).prop('checked') ? '1' : '0';
    } );
}