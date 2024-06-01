import DOMPurify from 'dompurify';
import parse from 'html-react-parser';

export const htmlFrom = (htmlString: string | undefined | null) => {
    if (htmlString) {
        const cleanHtmlString = DOMPurify.sanitize(htmlString, { USE_PROFILES: { html: true } });
        return parse(cleanHtmlString);
    }
    return "";
}