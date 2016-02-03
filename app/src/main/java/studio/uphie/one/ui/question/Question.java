package studio.uphie.one.ui.question;

/**
 * Created by Uphie on 2015/10/8.
 * Email uphie7@gmail.com
 */
public class Question {

    /**
     * ???
     */
    public QNCmt entQNCmt;
    /**
     * 最近更新时间
     */
    public String strLastUpdateDate;
    /**
     * ???
     */
    public String strDayDiffer;
    /**
     * 网页版链接
     */
    public String sWebLk;
    /**
     * 喜欢的数量
     */
    public int strPraiseNumber;
    /**
     * 问题id
     */
    public int strQuestionId;
    /**
     * 问题标题
     */
    public String strQuestionTitle;
    /**
     * 问题内容
     */
    public String strQuestionContent;
    /**
     * 回答标题
     */
    public String strAnswerTitle;
    /**
     * 回答内容
     */
    public String strAnswerContent;
    /**
     * 问题上架时间
     */
    public String strQuestionMarketTime;
    /**
     * 编辑
     */
    public String sEditor;

    public static class QNCmt {
        public String strCnt;
        public String strId;
        public String strD;
        public String pNum;
        public String upFg;
    }
}
